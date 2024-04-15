package gui

import scala.swing._
import scala.swing.event._
import javax.swing.ImageIcon
import services.{Jardinage, Parser}
import java.io.File
import java.awt.{Graphics2D, Image}


class ImagePanel(var imagePath: String) extends Panel {
  private var image: Image = new ImageIcon(imagePath).getImage

  def setImage(newImagePath: String): Unit = {
    image = new ImageIcon(newImagePath).getImage
    imagePath = newImagePath
    repaint()
  }

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)
    if (image != null) {
      g.drawImage(image, 0, 0, peer.getWidth, peer.getHeight, peer)
    }
  }
}


object TondeuseGUI extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Simulateur de Tondeuse"
    preferredSize = new Dimension(800, 600)


    val pelousePanel = new ImagePanel("resources/mow-your-lawn.jpg") {
      preferredSize = new Dimension(600, 300)
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    val button = new Button("Jardiner") {
      preferredSize = new Dimension(80, 40)
    }

    val fileChooser = new FileChooser(new File("."))
    fileChooser.title = "Choisir un fichier d'instructions"

    contents = new BoxPanel(Orientation.Vertical) {
      contents += pelousePanel
      contents += Swing.VStrut(10)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += button
      }
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    listenTo(button)

    reactions += {
      case ButtonClicked(`button`) =>
        val result = fileChooser.showOpenDialog(null)
        if (result == FileChooser.Result.Approve) {
          Parser.readInstructions(fileChooser.selectedFile.getAbsolutePath) match {
            case Right((pelouse, tondeuses)) =>
              pelousePanel.setImage("resources/after.jpg")
              tondeuses.zipWithIndex.foreach { case ((tondeuse, instructions), index) =>
                val result = Jardinage.executeInstructions(pelouse, tondeuse, instructions)
                Dialog.showMessage(contents.head, s"La tondeuse $index se trouve à (${result.position.x}, " +
                  s"${result.position.y}) orientée ${result.orientation}", title="Position Finale")
              }
              pelousePanel.setImage("resources/mow-your-lawn.jpg")
            case Left(errorMessage) =>
              Dialog.showMessage(contents.head, s"Erreur : $errorMessage", title="Erreur de Chargement", Dialog.Message.Error)
          }
        }
    }
  }
}
