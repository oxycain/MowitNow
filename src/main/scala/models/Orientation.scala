package models

sealed trait Orientation
object Orientation {
  case object North extends Orientation
  case object East extends Orientation
  case object South extends Orientation
  case object West extends Orientation

  def fromChar(c: Char): Option[Orientation] = c match {
    case 'N' => Some(North)
    case 'E' => Some(East)
    case 'S' => Some(South)
    case 'W' => Some(West)
    case _ => None

  }
}
