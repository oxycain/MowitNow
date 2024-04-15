import models.{Orientation, Pelouse, Position, Tondeuse}
import services.Parser
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParserTest extends AnyFlatSpec with Matchers {

  "readInstructions" should "correctly parse the input file into models" in {
    Parser.readInstructions("src/test/resources/test_instructions.txt") match {
      case Right((pelouse, tondeuses)) =>
        pelouse should be(Pelouse(5, 5))
        tondeuses.length should be(2)
        tondeuses.head._1 should be(Tondeuse(Position(1, 2), Orientation.North))
        tondeuses.head._2 should be("GAGAGAGAA")
      case Left(error) =>
        fail(s"Expected successful parsing, but failed with error: $error")
    }
  }

  it should "return an error if the file is empty" in {
    Parser.readInstructions("src/test/resources/empty.txt") match {
      case Right(_) =>
        fail("Expected failure for empty file but parsing succeeded.")
      case Left(error) =>
        error should include("Le fichier est vide")
    }
  }

  it should "return an error for incorrect 'Pelouse' dimensions" in {
    Parser.readInstructions("src/test/resources/missing_first.txt") match {
      case Right(_) =>
        fail("Expected failure for incorrect dimensions but parsing succeeded.")
      case Left(error) =>
        error should include("Dimensions manquantes ou incorrectes pour la pelouse")
    }
  }

  it should "return an error for invalid 'Tondeuse' data" in {
    Parser.readInstructions("src/test/resources/missing_second.txt") match {
      case Right(_) =>
        fail("Expected failure for invalid tondeuse data but parsing succeeded.")
      case Left(error) =>
        error should include("Données de tondeuse invalides ou incomplètes")
    }
  }

  it should "return an error for invalid orientation" in {
    Parser.readInstructions("src/test/resources/invalid_orientation.txt") match {
      case Right(_) =>
        fail("Expected failure for invalid orientation but parsing succeeded.")
      case Left(error) =>
        error should include("Orientation invalide")
    }
  }


  it should "return an error for invalid characters in instructions" in {
    Parser.readInstructions("src/test/resources/invalid_instruction_chars.txt") match {
      case Right(_) =>
        fail("Expected failure for invalid characters but parsing succeeded.")
      case Left(error) =>
        error should include("Instructions contiennent des caractères invalides")
    }
  }

  it should "return an error for non-existent file paths" in {
    Parser.readInstructions("src/test/resources/inexistent.txt") match {
      case Right(_) =>
        fail("Expected failure for non-existent file but parsing succeeded.")
      case Left(error) =>
        error should include("Erreur : Aucun fichier trouvé")
    }
  }
}

