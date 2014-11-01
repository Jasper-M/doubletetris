package doubletetris.controller

import doubletetris.Rectangle
import doubletetris.GameState
import doubletetris.Player
import doubletetris.Playing
import doubletetris.GameOver

class Controller(val size: Rectangle) {
  
  @volatile
  var state = GameState.init(size)
  
  def step() {
    state = state match{
      case playing: Playing => playing.step()
      case GameOver => state
    }
  }
  
  def moveLeft() {
    state = state match{
      case playing: Playing => playing.moveLeft()
      case GameOver => state
    }
  }
  
  def moveRight() {
    state = state match{
      case playing: Playing => playing.moveRight()
      case GameOver => state
    }
  }
  
  def moveUp(p: Player) {
    state = state match{
      case playing: Playing => playing.moveUp(p)
      case GameOver => state
    }
  }
  
  def moveDown(p: Player) {
    state = state match{
      case playing: Playing => playing.moveDown(p)
      case GameOver => state
    }
  }
  
  def rotate(p: Player) {
    state = state match{
      case playing: Playing => playing.rotate(p)
      case GameOver => state
    }
  }

}