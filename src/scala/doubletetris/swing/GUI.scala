package scala.doubletetris.swing

import scala.swing._
import scala.swing.Swing._
import java.awt.Color
import scala.swing.event.KeyPressed
import scala.swing.event.Key
import scala.doubletetris._
import scala.doubletetris.controller.Controller
import javax.swing.Timer
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

object GUI extends SimpleSwingApplication {
  
  val controller = new Controller(Rectangle(60, 20))
  val blockSize = 20
  val leftControls = ZQSD
  val rightControls = ARROWS
  
  override def top = new MainFrame {
    contents = new Panel{
      
      title = "Double Tetris"
      preferredSize = (controller.size.width*blockSize, controller.size.height*blockSize)
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
	    val state = controller.state
	    
	    for((p,block) <- state.blocks){
	      if(p == Left)
	        g.setColor(Color.WHITE)
	      else
	        g.setColor(Color.BLACK)
	      g.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
	    }
        
        for(block <- state.left.blocks){
	      g.setColor(Color.WHITE)
	      g.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
	    }
        
        for(block <- state.right.blocks){
	      g.setColor(Color.BLACK)
	      g.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
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