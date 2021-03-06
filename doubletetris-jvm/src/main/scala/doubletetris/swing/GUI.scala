package doubletetris.swing

import scala.swing._
import scala.swing.Swing._
import java.awt.Color
import scala.swing.event.KeyPressed
import scala.swing.event.Key
import doubletetris._
import doubletetris.controller.Controller
import javax.swing.Timer
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.Font

object GUI extends SimpleSwingApplication {
  
  val controller = new Controller(Rectangle(30, 10))
  val blockSize = 20
  val leftControls = WASD
  val rightControls = ARROWS
  
  override def top = mainFrame
  
  val mainFrame: MainFrame = new MainFrame {
    contents = new Panel{
      
      title = "Double Tetris"
      val width = controller.size.width*blockSize
      val height = controller.size.height*blockSize
      preferredSize = (width, height)
      background = new Color(100,100,100)
      
      val timer = new Timer(1000, new ActionListener(){
        override def actionPerformed(e: ActionEvent) {
          controller.step()
          repaint()
        }
      })
      timer.start()
      
      override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)
	    controller.state match {
          case Playing(left,right,blocks,nrLines) => {

            for ((p, block) <- blocks) {
              if (p == Left)
                g.setColor(Color.WHITE)
              else
                g.setColor(Color.BLACK)
              g.fillRect(block.x * blockSize, block.y * blockSize, blockSize, blockSize)
            }

            for (block <- left.blocks) {
              g.setColor(Color.WHITE)
              g.fillRect(block.x * blockSize, block.y * blockSize, blockSize, blockSize)
            }

            for (block <- right.blocks) {
              g.setColor(Color.BLACK)
              g.fillRect(block.x * blockSize, block.y * blockSize, blockSize, blockSize)
            }

            mainFrame.title = "Lines scored: " + nrLines
          }
          case GameOver => {
            timer.stop()
            g.setColor(Color.WHITE)
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            g.drawString("Game Over", width/2-75, height/2)
          }
        }
	    
      }
      this.focusable = true
      listenTo(keys)
    
      reactions += {
        case KeyPressed(_,key,_,_) => {
          if(key == leftControls.up) controller.moveUp(Left)
          else if(key == leftControls.down) controller.moveDown(Left)
          else if(key == leftControls.left) controller.rotate(Left)
          else if(key == leftControls.right) controller.moveRight()
          else if(key == rightControls.up) controller.moveUp(Right)
          else if(key == rightControls.down) controller.moveDown(Right)
          else if(key == rightControls.left) controller.moveLeft()
          else if(key == rightControls.right) controller.rotate(Right)
          repaint()
        }
      }
    }
  }

}