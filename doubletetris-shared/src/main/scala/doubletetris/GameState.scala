package doubletetris

import scala.annotation.tailrec

abstract class GameState

case object GameOver extends GameState

case class Playing(left: Tetromino, right: Tetromino, blocks: List[(Player, Block)], nrLines: Int)(implicit size: Rectangle) extends GameState {

  private def addToBlocks(p: Player): Playing = p match {
    case Left => 
      Playing(
          Tetromino.random(Block(1,size.height/2)),
          right,
          (List.fill(left.blocks.size)(Left) zip left.blocks) ++ blocks,
          nrLines
      )
    case Right => 
      Playing(
          left,
          Tetromino.random(Block(size.width-2,size.height/2)),
          (List.fill(right.blocks.size)(Right) zip right.blocks) ++ blocks,
          nrLines
      )
  }
  
  @tailrec
  final private[Playing] def processLines(): Playing = {
    val array = Array.fill(size.width)(0)
    for(block <- blocks.unzip._2) {
      array(block.x) = array(block.x) + 1
    }
    array.zipWithIndex.find{case (nrBlocks, index) => nrBlocks == size.height} match {
      case Some((_, index)) => 
        Playing(
          left,
          right,
          removeLine(blocks, index),
          nrLines+1
        ).processLines()
      case None => this
    }
  }
  
  private def removeLine(blocks: List[(Player, Block)], line: Int): List[(Player, Block)] =
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
  
  def moveLeft(): GameState = {
    val state = Playing(
        left,
        right.left,
        blocks,
        nrLines
    )
    
    val newState = if(state.hasOverlap())
      if(state.left overlaps state.right){
        this.addToBlocks(Right).addToBlocks(Left).processLines()
      }
      else
        this.addToBlocks(Right).processLines()
    else state
    
    if(!newState.right.isInBoundsOf(size) || newState.hasOverlap)
      GameOver
    else
      newState
  }
  
  def moveRight(): GameState = {
    val state = Playing(
        left.right,
        right,
        blocks,
        nrLines
    )
    
    val newState = if(state.hasOverlap())
      if(state.left overlaps state.right)
        this.addToBlocks(Left).addToBlocks(Right).processLines()
      else
        this.addToBlocks(Left).processLines()
    else state
    
    if(!newState.left.isInBoundsOf(size) || newState.hasOverlap)
      GameOver
    else
      newState
  }
    
  def moveUp(p: Player): GameState = {
    val state = p match {
      case Left =>
        Playing(
          left.up,
          right,
          blocks,
          nrLines)
      case Right =>
        Playing(
          left,
          right.up,
          blocks,
          nrLines)
    }
    if(state.left.isInBoundsOf(size) && state.right.isInBoundsOf(size) && !state.hasOverlap)
      state
    else
      this
  }
  
  def moveDown(p: Player): GameState = {
    val state = p match {
      case Left =>
        Playing(
          left.down,
          right,
          blocks,
          nrLines)
      case Right =>
        Playing(
          left,
          right.down,
          blocks,
          nrLines)
    }
    if(state.left.isInBoundsOf(size) && state.right.isInBoundsOf(size) && !state.hasOverlap)
      state
    else
      this
  }
  
  def rotate(p: Player): GameState = {
    val state = p match {
      case Left =>
        Playing(
          left.rotate,
          right,
          blocks,
          nrLines)
      case Right =>
        Playing(
          left,
          right.rotate,
          blocks,
          nrLines)
    }
    if(state.left.isInBoundsOf(size) && state.right.isInBoundsOf(size) && !state.hasOverlap)
      state
    else
      this
  }
  
  def step(): GameState = {
    var state = Playing(
        left.right,
        right,
        blocks,
        nrLines
    )
    
    if(state.hasOverlap())
      state = Playing.this.addToBlocks(Left).processLines()
    
    val newState = Playing(
        state.left,
        state.right.left,
        state.blocks,
        state.nrLines
    )
    
    val finalState = if(newState.hasOverlap())
      state.addToBlocks(Right).processLines()
    else
      newState
      
    if(finalState.hasOverlap() || !finalState.left.isInBoundsOf(size) || !finalState.right.isInBoundsOf(size))
      GameOver
    else
      finalState
  }
  
  private[Playing] def hasOverlap(): Boolean = 
    (left overlaps right) ||
    blocks.unzip._2.exists( b => (left overlaps b) || (right overlaps b))
  
}

object GameState {
  
  def init(size: Rectangle): GameState = 
    Playing(
        Tetromino.random(Block(1,size.height/2)),
        Tetromino.random(Block(size.width-2,size.height/2)),
        List.empty,
        0
    )(size)
}

sealed abstract class Player

case object Left extends Player
case object Right extends Player