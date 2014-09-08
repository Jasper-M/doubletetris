package doubletetris.canvas

import scala.scalajs.js
import js.Dynamic.{ global => g }
import js.annotation.JSExport
import doubletetris._
import doubletetris.controller._

@JSExport
object Main {

  var leftControls: Controls = WASD
  val rightControls = ARROWS
  val canvas = g.document.createElement("canvas")
  val score = g.document.createElement("span")
  val controller = new Controller(Rectangle(30, 10))
  val blockSize = 20
  var stopTimer = false

  @JSExport
  def main() {
    canvas.width = controller.size.width*blockSize
    canvas.height = controller.size.height*blockSize
    
    val scoreP = g.document.createElement("p")
    val scorePText = g.document.createTextNode("Lines scored: ")
    val scoreText = g.document.createTextNode("0")
    scoreP.appendChild(scorePText)
    score.appendChild(scoreText)
    scoreP.appendChild(score)
    
    val qwerty = g.document.createElement("input")
    qwerty.`type` = "radio"
    qwerty.name = "keyboard"
    qwerty.value = "qwerty"
    qwerty.id = "qwerty"
    qwerty.checked = "checked"
    qwerty.onchange = () => kbLayoutChanged("qwerty")
    val qwertylabel = g.document.createElement("label")
    qwertylabel.appendChild(qwerty)
    qwertylabel.appendChild(g.document.createTextNode("qwerty"))
    val azerty = g.document.createElement("input")
    azerty.`type` = "radio"
    azerty.name = "keyboard"
    azerty.value = "azerty"
    azerty.id = "azerty"
    azerty.onchange = () => kbLayoutChanged("azerty")
    val azertylabel = g.document.createElement("label")
    azertylabel.appendChild(azerty)
    azertylabel.appendChild(g.document.createTextNode("azerty"))
    
    val app = g.document.getElementById("doubletetris_app")
    app.appendChild(scoreP)
    app.appendChild(canvas)
    app.appendChild(g.document.createElement("br"))
    app.appendChild(qwertylabel)
    app.appendChild(azertylabel)
    
    
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
  
  private def kbLayoutChanged(e: String) {
    leftControls = e match {
      case "qwerty" => WASD
      case "azerty" => ZQSD
    }
  }
  
  private def step() {
    controller.step()
    repaint()
    if(!stopTimer) g.window.setTimeout(() => step(), 1000)
  }
  
  private def repaint() {
    val context = canvas.getContext("2d")
    
    context.fillStyle = "rgb(100,100,100)"
    context.fillRect(0, 0, canvas.width, canvas.height)
    
    controller.state match {
      case Playing(left,right,blocks,nrLines) => {
      
        for((p,block) <- blocks){
          if(p == Left)
            context.fillStyle = "white"
          else
            context.fillStyle = "black"
          context.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
        }
          
        for(block <- left.blocks){
          context.fillStyle = "white"
          context.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
        }
          
        for(block <- right.blocks){
          context.fillStyle = "black"
          context.fillRect(block.x*blockSize, block.y*blockSize, blockSize, blockSize)
        }
        
        score.innerHTML = nrLines
      }
      case GameOver => {
        stopTimer = true
        context.fillStyle = "white";
        context.font = "bold 30pt monospace";
        context.fillText("Game Over", canvas.width/2-100, canvas.height/2);
      }
    }
	  
  }

}

trait KeyBoardEvent extends js.Object {
  val keyCode: js.Number = ???
}
