package doubletetris.canvas

import scala.scalajs.js
import js.annotation.JSExport
import doubletetris._
import doubletetris.controller._
import org.scalajs.dom._, ext.Castable

@JSExport("Main")
object Main {

  var leftControls: Controls = WASD
  val rightControls = ARROWS
  val canvas = document.createElement("canvas").cast[html.Canvas]
  val score = document.createElement("span").cast[html.Element]
  val controller = new Controller(Rectangle(30, 10))
  val blockSize = 20
  var stopTimer = false
  
  val qwerty = document.createElement("input").cast[html.Input]
  val azerty = document.createElement("input").cast[html.Input]

  @JSExport
  def main() {
    canvas.width = controller.size.width*blockSize
    canvas.height = controller.size.height*blockSize
    
    val scoreP = document.createElement("p")
    val scorePText = document.createTextNode("Lines scored: ")
    val scoreText = document.createTextNode("0")
    scoreP.appendChild(scorePText)
    score.appendChild(scoreText)
    scoreP.appendChild(score)
    
    //val qwerty = g.document.createElement("input")
    qwerty.`type` = "radio"
    qwerty.name = "keyboard"
    qwerty.value = "qwerty"
    qwerty.id = "qwerty"
    qwerty.checked = true
    qwerty.onchange = (e: Event) => kbLayoutChanged("qwerty")
    val qwertylabel = document.createElement("label")
    qwertylabel.appendChild(qwerty)
    qwertylabel.appendChild(document.createTextNode("qwerty"))
    //val azerty = g.document.createElement("input")
    azerty.`type` = "radio"
    azerty.name = "keyboard"
    azerty.value = "azerty"
    azerty.id = "azerty"
    azerty.onchange = (e: Event) => kbLayoutChanged("azerty")
    val azertylabel = document.createElement("label")
    azertylabel.appendChild(azerty)
    azertylabel.appendChild(document.createTextNode("azerty"))
    
    val app = document.getElementById("doubletetris_app")
    app.appendChild(scoreP)
    app.appendChild(canvas)
    app.appendChild(document.createElement("br"))
    app.appendChild(qwertylabel)
    app.appendChild(azertylabel)
    
    
    initTimer()
    
    window.addEventListener("keydown", keyPressed _, false)
    
    repaint()
  }
  
  private def keyPressed(e: KeyboardEvent) {
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
      case "qwerty" => 
        qwerty.blur()
        WASD
      case "azerty" => 
        azerty.blur()
        ZQSD
    }
    
  }
  
  private def initTimer() {
    window.setTimeout(step _, 1000)
  }
  
  private def step() {
    controller.step()
    repaint()
    if(!stopTimer) initTimer()
  }
  
  private def repaint() {
    val context = canvas.getContext("2d").cast[CanvasRenderingContext2D]
    
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
        
        score.innerHTML = nrLines.toString
      }
      case GameOver => {
        stopTimer = true
        context.fillStyle = "white"
        context.font = "bold 30pt monospace"
        context.fillText("Game Over", canvas.width/2-100, canvas.height/2)
      }
    }
	  
  }

}
