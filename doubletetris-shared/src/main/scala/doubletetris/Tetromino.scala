package doubletetris

import scala.util.Random

class Tetromino(states: IndexedSeq[Set[Block]], state: Int) {
  
	require(states.length >= 1 && states.forall(_.size == 4))
	
	def blocks = states(state)
	
	def down() = new Tetromino(states.map(_.map(_.down)), state)
	
	def left() = new Tetromino(states.map(_.map(_.left)), state)
	
	def right() = new Tetromino(states.map(_.map(_.right)), state)
	
	def up() = new Tetromino(states.map(_.map(_.up)), state)
	
	def rotate() = new Tetromino(states, if(state==states.length-1) 0 else state+1)
	
	def overlaps(that: Tetromino): Boolean = that.blocks.exists(this.overlaps(_))
	
	def overlaps(block: Block): Boolean = this.blocks.exists(block overlaps _)
	
	def isInBoundsOf(rect: Rectangle) = this.blocks.forall(_.isInBoundsOf(rect))
}

object Tetromino {
  val rand = new Random()
  
  def random(block: Block) = rand.nextInt(7) match {
    case 0 => I(block)
    case 1 => Square(block)
    case 2 => Zleft(block)
    case 3 => Zright(block)
    case 4 => Lleft(block)
    case 5 => Lright(block)
    case 6 => T(block)
  }
}

object I{
  def apply(block: Block) = new Tetromino(IndexedSeq(Set(block.up, block, block.down, block.down.down),
                                                  Set(block.down.left, block.down.left.left, block.down, block.down.right),
                                                  Set(block.up.left, block.left, block.down.left, block.down.down.left),
                                                  Set(block.left, block.left.left, block, block.right)), 0)
}

object Square{
  def apply(block: Block) = new Tetromino(IndexedSeq(Set(block, block.right, block.down, block.down.right)), 0)
}

object Zleft{
  def apply(block: Block) = new Tetromino(IndexedSeq(Set(block.left.up, block.up, block, block.right),
                                                  Set(block.down, block.right, block, block.right.up),
                                                  Set(block.left, block.down, block, block.right.down),
                                                  Set(block.left.down, block.left, block, block.up)), 0)
}

object Zright{
  def apply(block: Block) = new Tetromino(IndexedSeq(Set(block.left, block.up, block, block.up.right),
                                                  Set(block.up, block.right, block, block.right.down),
                                                  Set(block.left.down, block.down, block, block.right),
                                                  Set(block.left.up, block.left, block, block.down)), 0)
}

object Lleft{
  def apply(block: Block) = new Tetromino(IndexedSeq(Set(block.left.down, block.down, block, block.up),
                                                  Set(block.left.up, block.left, block, block.right),
                                                  Set(block.up.right, block.up, block, block.down),
                                                  Set(block.left, block.right, block, block.right.down)), 0)
}

object Lright{
  def apply(block: Block) = new Tetromino(IndexedSeq(Set(block.right.down, block.down, block, block.up),
                                                  Set(block.left.down, block.left, block, block.right),
                                                  Set(block.left.up, block.up, block, block.down),
                                                  Set(block.left, block.right, block, block.right.up)), 0)
}

object T{
  def apply(block: Block) = new Tetromino(IndexedSeq(Set(block.left, block, block.right, block.up),
                                                  Set(block.up, block.right, block, block.down),
                                                  Set(block.left, block.down, block, block.right),
                                                  Set(block.left, block.up, block, block.down)), 0)
}
