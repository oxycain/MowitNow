import models.{Orientation, Pelouse, Position, Tondeuse}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import services.Jardinage

class JardinageTest extends AnyFlatSpec with Matchers {

  "readInstructions" should "correctly parse the input file into models" in {
    val (pelouse, tondeuses) = Jardinage.readInstructions("src/test/resources/test_instructions.txt")
    pelouse should be(Pelouse(5, 5))
    tondeuses.length should be(2)
    tondeuses.head._1 should be(Tondeuse(Position(1, 2), Orientation.North))
    tondeuses.head._2 should be("GAGAGAGAA")
  }

  "executeInstructions" should "correctly execute movements and rotations for a single mower" in {
    val initialPelouse = Pelouse(5, 5)
    val initialTondeuse = Tondeuse(Position(1, 2), Orientation.North)
    val instructions = "GAGAGAGAA"

    val resultTondeuse = Jardinage.executeInstructions(initialPelouse, initialTondeuse, instructions)
    resultTondeuse.position should be(Position(1, 3))
    resultTondeuse.orientation should be(Orientation.North)
  }
}
