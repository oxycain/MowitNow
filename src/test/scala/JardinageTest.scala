import models.{Orientation, Pelouse, Position, Tondeuse}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import services.Jardinage

class JardinageTest extends AnyFlatSpec with Matchers {

  "executeInstructions" should "correctly move the 'Tondeuse' based on instructions" in {
    val pelouse = Pelouse(5, 5)
    val startingPosition = Position(1, 2)
    val startingOrientation = Orientation.North
    val tondeuse = Tondeuse(startingPosition, startingOrientation)
    val instructions = "GAGAGAGAA"

    val expectedResult = Tondeuse(Position(1, 3), Orientation.North)
    val result = Jardinage.executeInstructions(pelouse, tondeuse, instructions)

    result.position.x should be(expectedResult.position.x)
    result.position.y should be(expectedResult.position.y)
    result.orientation should be(expectedResult.orientation)
  }

  it should "not move the 'Tondeuse' beyond the boundaries of the Pelouse" in {
    val pelouse = Pelouse(2, 2)
    val tondeuse = Tondeuse(Position(2, 2), Orientation.North)
    val instructions = "A"

    val result = Jardinage.executeInstructions(pelouse, tondeuse, instructions)

    // it should not move beyond 2, 2 to the north
    result.position.x should be(2)
    result.position.y should be(2)
  }

  it should "ignore invalid commands and maintain state" in {
    val initialPosition = Position(1, 1)
    val pelouse = Pelouse(5, 5)
    val tondeuse = Tondeuse(initialPosition, Orientation.North)
    val result = Jardinage.executeInstructions(pelouse, tondeuse, "XYZ")
    result.position should be(initialPosition)
    result.orientation should be(Orientation.North)
  }

  it should "process a complex instruction sequence correctly" in {
    val initialPosition = Position(1, 1)
    val pelouse = Pelouse(5, 5)
    val tondeuse = Tondeuse(initialPosition, Orientation.North)
    val instructions = "AAGADADDA"
    val result = Jardinage.executeInstructions(pelouse, tondeuse, instructions)
    result.position should be(Position(0, 3))
    result.orientation should be(Orientation.South)
  }
}
