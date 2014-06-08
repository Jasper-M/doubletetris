package scala.doubletetris

case class GameState(left: Tetromino, right: Tetromino, blocks: List[(Player, Block)])(implicit size: Rectangle) {

  private def addToBlocks(p: Player): GameState = p match {
    case Left => 
      GameState(
          Tetromino.random(Block(1,size.height/2)),
          right,
          (List.fill(left.blocks.size)(Left) zip left.blocks) ++ blocks
      )
    case Right => 
      GameState(
          left,
          Tetromino.random(Block(size.width-2,size.height/2)),
          (List.fill(right.blocks.size)(Right) zip right.blocks) ++ blocks
      )
  }
  
  private def processLines(): GameState = {
    val array = Array.fill(size.width)(0)
    for(block <- blocks.unzip._2){
      array(block.x) = array(block.x) + 1
    }
    var newBlocks = blocks
    for((nrBlocks, index) <- array.zipWithIndex){
      if(nrBlocks == size.height)
        newBlocks = removeLine(newBlocks, index)
    }
    GameState(
        left,
        right,
        newBlocks
    )
  }
  
  private def removeLine(blocks: List[(Player, Block)], line: Int): List[(Player, Block)] = {
    blocks.flatMap(
      _ match {
        case (p, block) if block.x == line => None
        case (p, block) if line < size.width/2 => 
          if(block.x < line)
            Some(p, block.right)
          else
            Some(p, block)
        case (p, block) if line >= size.width/2 => 
          if(block.x > line)
            Some(p, block.left)
          else
            Some(p, block)
      }
    )
  }
  
  def moveLeft(): GameState = {
    val state = GameState(
        left,
        right.left,
        blocks
    )
    if(state.hasOverlap())
      if(state.left overlaps state.right)
        this.addToBlocks(Right).addToBlocks(Left).processLines()
      else
        this.addToBlocks(Right).processLines()
    else state
  }
  
  def moveRight(): GameState = {
    val state = GameState(
        left.right,
        right,
        blocks
    )
    if(state.hasOverlap())
      if(state.left overlaps state.right)
        this.addToBlocks(Left).addToBlocks(Right).processLines()
      else
        this.addToBlocks(Left).processLines()
    else state
  }
    
  def moveUp(p: Player): GameState = {
    val state = p match {
      case Left =>
        GameState(
          left.up,
          right,
          blocks)
      case Right =>
        GameState(
          left,
          right.up,
          blocks)
    }
    if(state.left.isInBoundsOf(size) && state.right.isInBoundsOf(size) && !state.hasOverlap)
      state
    else
      this
  }
  
  def moveDown(p: Player): GameState = {
    val state = p match {
      case Left =>
        GameState(
          left.down,
          right,
          blocks)
      case Right =>
        GameState(
          left,
          right.down,
          blocks)
    }
    if(state.left.isInBoundsOf(size) && state.right.isInBoundsOf(size) && !state.hasOverlap)
      state
    else
      this
  }
  
  def rotate(p: Player): GameState = {
    val state = p match {
      case Left =>
        GameState(
          left.rotate,
          right,
          blocks)
      case Right =>
        GameState(
          left,
          right.rotate,
          blocks)
    }
    if(state.left.isInBoundsOf(size) && state.right.isInBoundsOf(size) && !state.hasOverlap)
      state
    else
      this
  }
  
  def step(): GameState = {
    var state = GameState(
        left.right,
        right,
        blocks
    )
    
    if(state.hasOverlap())
      state = this.addToBlocks(Left).processLines()
    
    val newState = GameState(
        state.left,
        state.right.left,
        state.blocks
    )
    
    if(newState.hasOverlap())
      state.addToBlocks(Right).processLines()
    else
      newState
  }
  
  private[GameState] def hasOverlap(): Boolean = 
    (left overlaps right) ||
    blocks.unzip._2.exists(left overlaps _) ||
    blocks.unzip._2.exists(right overlaps _)
  
}

object GameState {
  
  def init(size: Rectangle) = 
    GameState(
        Tetromino.random(Block(1,size.height/2)),
        Tetromino.random(Block(size.width-2,size.height/2)),
        List.empty
    )(size)
}

sealed abstract class Player

case object Left extends Player
case object Right extends Player