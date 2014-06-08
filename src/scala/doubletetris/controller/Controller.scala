package scala.doubletetris.controller

import scala.doubletetris.Rectangle
import scala.doubletetris.GameState
import scala.doubletetris.Player

class Controller(val size: Rectangle) {
  
  var state = GameState.init(size)
  
  def step() {
    state = state.step()
  }
  
  def moveLeft() {
    state = state.moveLeft()
  }
  
  def moveRight() {
    state = state.moveRight()
  }
  
  def moveUp(p: Player) {
    state = state.moveUp(p)
  }
  
  def moveDown(p: Player) {
    state = state.moveDown(p)
  }
  
  def rotate(p: Player) {
    state = state.rotate(p)
  }

}