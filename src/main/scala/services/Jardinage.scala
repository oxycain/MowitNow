package services

import models.{Pelouse, Tondeuse}
import services.Parser.readInstructions
import scala.io.StdIn

object Jardinage {
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

    readInstructions(filePath) match {
      case Right((pelouse, tondeuses)) =>
        tondeuses.zipWithIndex.foreach { case ((tondeuse, instructions), index) =>
          val result = executeInstructions(pelouse, tondeuse, instructions)
          println(s"C'est la fin du jardinage : La tondeuse ${index + 1} est à la position (${result.position.x}, ${result.position.y}) et est orientée vers ${result.orientation}")
        }
      case Left(errorMessage) =>
        println(s"Erreur lors de la lecture ou du traitement du fichier: $errorMessage")
    }
  }
}
