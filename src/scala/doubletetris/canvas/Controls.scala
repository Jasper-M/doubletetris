package scala.doubletetris.canvas

abstract class Controls {

  def up: Int
  
  def down: Int
  
  def left: Int
  
  def right: Int
}

object ARROWS extends Controls {
  
  def up = 38
  
  def down = 40
  
  def left = 37
  
  def right = 39
}

object WASD extends Controls {
  
  def up = 87
  
  def down = 83
  
  def left = 65
  
  def right = 68
}

object ZQSD extends Controls {
  
  def up = 90
  
  def down = 83
  
  def left = 81
  
  def right = 68
}
