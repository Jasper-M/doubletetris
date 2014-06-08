package scala.doubletetris.swing

import scala.swing.event.Key

abstract class Controls {

  def up: Key.Value
  
  def down: Key.Value
  
  def left: Key.Value
  
  def right: Key.Value
}

object ARROWS extends Controls {
  
  def up = Key.Up
  
  def down = Key.Down
  
  def left = Key.Left
  
  def right = Key.Right
}

object WASD extends Controls {
  
  def up = Key.W
  
  def down = Key.S
  
  def left = Key.A
  
  def right = Key.D
}

object ZQSD extends Controls {
  
  def up = Key.Z
  
  def down = Key.S
  
  def left = Key.Q
  
  def right = Key.D
}