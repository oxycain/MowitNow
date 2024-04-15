package services

import models.{Orientation, Pelouse, Position, Tondeuse}
import scala.io.Source
import scala.util.{Try, Failure, Success}

object Parser {
  def readInstructions(filePath: String): Either[String, (Pelouse, List[(Tondeuse, String)])] = {
  val source = Source.fromFile(filePath)
  Try {
    try {
      val lines = source.getLines().toList
      if (lines.isEmpty) {
        throw new IllegalArgumentException("Le fichier est vide")
      }

      val dimensions = lines.headOption.flatMap { line =>
        val parts = line.split(" ").map(_.trim).filter(_.nonEmpty).map(_.toInt)
        if (parts.length == 2) Some(parts) else None
      }.getOrElse(throw new IllegalArgumentException("Dimensions manquantes ou incorrectes pour la pelouse"))

      val pelouse = Pelouse(dimensions(0), dimensions(1))

      val tondeuses = lines.tail.grouped(2).toList.flatMap {
        case List(line1, line2) =>
          val posData = line1.split(" ").map(_.trim).filter(_.nonEmpty)
          if (posData.length != 3) throw new IllegalArgumentException("Données de tondeuse invalides ou incomplètes")

          val position = Position(posData(0).toInt, posData(1).toInt)
          val orientation = Orientation.fromChar(posData(2).head).getOrElse(throw new IllegalArgumentException("Orientation invalide"))

          if (!line2.matches("[AGD]+")) {
            throw new IllegalArgumentException("Instructions contiennent des caractères invalides")
          }

          Some(Tondeuse(position, orientation), line2)
        case List(_, _) =>
          throw new IllegalArgumentException("Instructions manquantes pour une tondeuse")
        case _ =>
          throw new IllegalArgumentException("Données de tondeuse incomplètes")
      }

      Right((pelouse, tondeuses))
    } finally {
      source.close()
    }
  } match {
    case Success(value) => value
    case Failure(ex) => Left(ex.getMessage)
  }
  }
}
