import models.{Orientation, Pelouse, Position, Tondeuse}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import services.Parser


class ParserTest extends AnyFlatSpec with Matchers  {

  "readInstructions" should "correctly parse the input file into models" in {
    val (pelouse, tondeuses) = Parser.readInstructions("src/test/resources/test_instructions.txt")
    pelouse should be(Pelouse(5, 5))
    tondeuses.length should be(2)
    tondeuses.head._1 should be(Tondeuse(Position(1, 2), Orientation.North))
    tondeuses.head._2 should be("GAGAGAGAA")
  }
}
