package scala.doubletetris.canvas

import scala.scalajs.js
import js.Dynamic.{ global => g }
import js.annotation.JSExport
import scala.doubletetris._
import scala.doubletetris.controller._

@JSExport
object Main {

  val leftControls = ZQSD
  val rightControls = ARROWS
  val canvas = g.document.getElementById("doubletetris_canvas")
  val controller = new Controller(Rectangle(30, 10))
  val blockSize = 20
  
  @JSExport
  def main() {
    canvas.width = controller.size.width*blockSize
    canvas.height = controller.size.height*blockSize
    
    g.window.setTimeout(() => step(), 1000)
    
    g.window.addEventListener("keydown", keyPressed(_:KeyBoardEvent), false)
    
    repaint()
  }
  
  private def keyPressed(e: KeyBoardEvent) {
    if(e.keyCode == leftControls.up) controller.moveUp(Left)
    else if(e.keyCode == leftControls.left) controller.rotate(Left)
    else if(e.keyCode == leftControls.down) controller.moveDown(Left)
    else if(e.keyCode == leftControls.right) controller.moveRight()
    else if(e.keyCode == rightControls.up) controller.moveUp(Right)
    else if(e.keyCode == rightControls.left) controller.moveLeft()
    else if(e.keyCode == rightControls.down) controller.moveDown(Right)
    else if(e.keyCode == rightControls.right) controller.rotate(Right)
    repaint()
  }
  
  private def step() {
    controller.step()
    repaint()
    g.window.setTimeout(() => step(), 1000)
  }
  
  private def repaint() {
    val state = controller.state
	  val context = canvas.getContext("2d")
	  
	  context.fillStyle = "rgb(100,100,100)"
	  context.fillRect(0, 0, canvas.width, canvas.height)
	  
    for((p,block) <- state.blocks){
      if(p == Left)
        context.fillStyle = "white"
      else
        context.fillStyle = "black"
      context.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
    }
      
    for(block <- state.left.blocks){
      context.fillStyle = "white"
      context.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
    }
      
    for(block <- state.right.blocks){
      context.fillStyle = "black"
      context.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
    }
  }

}

trait KeyBoardEvent extends js.Object {
  val keyCode: js.Number = ???
}
