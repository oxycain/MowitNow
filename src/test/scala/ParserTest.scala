import models.{Orientation, Pelouse, Position, Tondeuse}
import services.Parser
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.FileNotFoundException

class ParserTest extends AnyFlatSpec with Matchers {

  "readInstructions" should "correctly parse the input file into models" in {
    val (pelouse, tondeuses) = Parser.readInstructions("src/test/resources/test_instructions.txt")
    pelouse should be(Pelouse(5, 5))
    tondeuses.length should be(2)
    tondeuses.head._1 should be(Tondeuse(Position(1, 2), Orientation.North))
    tondeuses.head._2 should be("GAGAGAGAA")
  }

  it should "throw an IllegalArgumentException if the file is empty" in {
    intercept[IllegalArgumentException] {
      Parser.readInstructions("src/test/resources/emptyy.txt")
    }
  }

  it should "throw an IllegalArgumentException for incorrect 'Pelouse' dimensions" in {
    intercept[IllegalArgumentException] {
      Parser.readInstructions("src/test/resources/missing_first.txt")
    }
  }

  it should "throw an IllegalArgumentException for invalid 'Tondeuse' data" in {
    intercept[IllegalArgumentException] {
      Parser.readInstructions("src/test/resources/missing_second.txt")
    }
  }

  it should "throw an IllegalArgumentException for invalid orientation" in {
    intercept[IllegalArgumentException] {
      Parser.readInstructions("src/test/resources/invalid_orientation.txt")
    }
  }

  it should "handle files with missing 'Tondeuse' instructions" in {
    intercept[IllegalArgumentException] {
      Parser.readInstructions("src/test/resources/missing_mower.txt")
    }
  }

  it should "detect invalid characters in instructions" in {
    intercept[IllegalArgumentException] {
      Parser.readInstructions("src/test/resources/invalid_instruction_chars.txt")
    }
  }

  it should "throw a RuntimeException for non-existent file paths" in {
    intercept[FileNotFoundException] {
      Parser.readInstructions("src/test/resources/inexistent.txt")
    }
  }
}
