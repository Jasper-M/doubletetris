package scala.doubletetris

case class Block(x: Int, y: Int) {
  
  def up() = Block(x, y-1)
  
  def down() = Block(x, y+1)
  
  def left() = Block(x-1, y)
  
  def right() = Block(x+1, y)
  
  /**
   * Returns true is this block collides with that block, which belongs to Player p
   */
  def collides(that: Block, p: Player) = p match {
    case Left => this.y == that.y && this.x-that.x == 1
    case Right => this.y == that.y && this.x-that.x == -1
  }
  
  def overlaps(that: Block) = this == that
    
  def isInBoundsOf(rect: Rectangle) = 
    this.x >= 0 && this.y >=0 && this.x < rect.width && this.y < rect.height
}