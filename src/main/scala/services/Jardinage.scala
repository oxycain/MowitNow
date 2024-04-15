package services

import models.{Orientation, Pelouse, Position, Tondeuse}
import scala.io.Source
import scala.io.StdIn

object Jardinage {
  def readInstructions(filePath: String): (Pelouse, List[(Tondeuse, String)]) = {
    val lines = Source.fromFile(filePath).getLines().toList
    val dimensions = lines.head.split(" ").map(_.toInt)
    val pelouse = Pelouse(dimensions(0), dimensions(1))

    val tondeuses = lines.tail.grouped(2).map {
      case List(line1, line2) =>
        val posData = line1.split(" ")
        val position = Position(posData(0).toInt, posData(1).toInt)
        val orientation = Orientation.fromChar(posData(2).head).getOrElse(throw new IllegalArgumentException("Orientation invalide"))
        val tondeuse = Tondeuse(position, orientation)
        (tondeuse, line2)
    }.toList

    (pelouse, tondeuses)
  }

  def executeInstructions(pelouse: Pelouse, tondeuse: Tondeuse, instructions: String): Tondeuse = {
    instructions.foldLeft(tondeuse) { (currentTondeuse, instruction) =>
      instruction match {
        case 'A' => currentTondeuse.moveForward(pelouse)
        case 'G' => currentTondeuse.rotateLeft
        case 'D' => currentTondeuse.rotateRight
        case _ => currentTondeuse
      }
    }
  }

  def main(args: Array[String]): Unit = {
    println("Veuillez entrer le chemin du fichier d'instructions:")
    val filePath = StdIn.readLine()

    try {
      val (pelouse, tondeuses) = readInstructions(filePath)
      tondeuses.zipWithIndex.foreach { case ((tondeuse, instructions), index) =>
        val result = executeInstructions(pelouse, tondeuse, instructions)
        println(s"C'est la fin du jardinage : La tondeuse ${index + 1} est à la position (${result.position.x}, ${result.position.y}) et est orientée vers ${result.orientation}")
      }
    } catch {
      case ex: Exception => println(s"Erreur lors de la lecture ou du traitement du fichier: ${ex.getMessage}")
    }
  }
}
