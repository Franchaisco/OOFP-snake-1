package snake.logic

import engine.graphics.Rectangle
import engine.random.{RandomGenerator, ScalaRandomGen}
import snake.game.SnakeGame
import snake.logic.GameLogic._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer




/** To implement Snake, complete the ``TODOs`` below.
 *
 * If you need additional files,
 * please also put them in the ``snake`` package.
 */

class GameLogic(val random: RandomGenerator,
                         val gridDims : Dimensions) {
  var curDir: Direction = East()
  var curPoint: Point = Point(2, 0)
  var snakeBodyParts: List[Point] = List(Point(2, 0), Point(1, 0), Point(0, 0))
  var applePoint: Point = changeApplePoint()
  var expansion = 3
  var gameIsOver = false



  def gameOver: Boolean = {
    if(gameIsOver)
      {
        true
      }
    else
      {
        false
      }
  }




  def step(): Unit = {
    var appleEaten = false
    curPoint = Point(curPoint.x + curDir.toPoint.x, curPoint.y + curDir.toPoint.y)

    if(snakeBodyParts.contains(curPoint) && snakeBodyParts.last != curPoint)
      {
        gameIsOver = true
      }




    if (curPoint.x == gridDims.width) {
      curPoint = Point(0, curPoint.y)
    }
    else if (curPoint.x < 0) {
      curPoint = Point(gridDims.width - 1, curPoint.y)
    }
    else if (curPoint.y < 0) {
      curPoint = Point(curPoint.x, gridDims.height - 1)
    }
    else if (curPoint.y == gridDims.height) {
      curPoint = Point(curPoint.x, 0)
    }

    else if (curPoint == applePoint) {
      appleEaten = true
    }

    if (expansion < 3) {
      snakeBodyParts = curPoint :: snakeBodyParts
      expansion += 1
    }
    else {
      snakeBodyParts = curPoint :: snakeBodyParts.init
    }

    if(appleEaten)
      {
        applePoint = changeApplePoint()
        expansion = 0
        appleEaten= false
      }

    if (gridDims.allPointsInside.size == snakeBodyParts.size) {
      gameIsOver = true
    }



  }





  def changeApplePoint(): Point = {
    var nrFreeSpots = 0
    var occupiedSpots = 0
    val totalBoardSize = gridDims.allPointsInside.size

    val freeSpots = ArrayBuffer[Point]()



        for(i <- gridDims.allPointsInside)
        {
          if (!(snakeBodyParts.contains(i))) {
            freeSpots += i
          }
          else {
            occupiedSpots += 1
          }
        }



    nrFreeSpots = totalBoardSize - occupiedSpots
    val newSpot = freeSpots(random.randomInt(nrFreeSpots))


    newSpot

  }




  def changeDir(d: Direction): Unit = {
    checkIfOpposite(d)


    d match {
      case North() => curDir = North()
      case West() => curDir = West()
      case East() => curDir = East()
      case South() => curDir = South()
    }
  }

  def checkIfOpposite(newDir : Direction) : Unit =
    {
      if(newDir == curDir.opposite)
        {
          gameIsOver = true
        }
    }


  def getCellType(p: Point): CellType = (
    if (p == curPoint) {
      SnakeHead(curDir)
    }
    else if (snakeBodyParts.contains(p)) {
      SnakeBody()
    }



    else if (p == applePoint) {
      Apple()
    }

    else {
      Empty()
    }

    )


  def setReverse(r: Boolean): Unit = ()

}

/** GameLogic companion object */
object GameLogic {

  val FramesPerSecond: Int = 5 // change this to increase/decrease speed of game

  val DrawSizeFactor = 1.0 // increase this to make the game bigger (for high-res screens)
  // or decrease to make game smaller

  // These are the dimensions used when playing the game.
  // When testing the game, other dimensions are passed to
  // the constructor of GameLogic.
  //
  // DO NOT USE the variable DefaultGridDims in your code!
  //
  // Doing so will cause tests which have different dimensions to FAIL!
  //
  // In your code only use gridDims.width and gridDims.height
  // do NOT use DefaultGridDims.width and DefaultGridDims.height
  val DefaultGridDims
    : Dimensions =
    Dimensions(width = 25, height = 25)  // you can adjust these values to play on a different sized board



}


