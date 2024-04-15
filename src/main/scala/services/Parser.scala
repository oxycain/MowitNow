package services

import models.{Orientation, Pelouse, Position, Tondeuse}
import scala.io.Source

object Parser {
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
}
