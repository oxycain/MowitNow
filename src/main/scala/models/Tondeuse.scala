package models

case class Tondeuse(position: Position, orientation: Orientation) {
  def rotateLeft: Tondeuse = this.copy(orientation = orientation match {
    case Orientation.North => Orientation.West
    case Orientation.West => Orientation.South
    case Orientation.South => Orientation.East
    case Orientation.East => Orientation.North
  })

  def rotateRight: Tondeuse = this.copy(orientation = orientation match {
    case Orientation.North => Orientation.East
    case Orientation.East => Orientation.South
    case Orientation.South => Orientation.West
    case Orientation.West => Orientation.North
  })

  def moveForward(pelouse: Pelouse): Tondeuse = {
    val newPosition = orientation match {
      case Orientation.North => position.copy(y = position.y + 1)
      case Orientation.East => position.copy(x = position.x + 1)
      case Orientation.South => position.copy(y = position.y - 1)
      case Orientation.West => position.copy(x = position.x - 1)
    }
    if (newPosition.x >= 0 && newPosition.x <= pelouse.largeur && newPosition.y >= 0 && newPosition.y <= pelouse.hauteur) {
      this.copy(position = newPosition)
    } else {
      this
    }
  }
}
