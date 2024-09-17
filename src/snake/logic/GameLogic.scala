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
  var applePoint: Point = changeApplePoint()
//  var snakeBody:


  def gameOver: Boolean = false

  // TODO implement me

  def computeNextPoint(): Point = {
    var nextPoint: Point = Point(0, 0)

    if (curDir == East()) {
      nextPoint = Point(curPoint.x - 1, curPoint.y)
    }
    else if (curDir == West()) {
      nextPoint = Point(curPoint.x + 1, curPoint.y)
    }
    else if (curDir == South()) {
      nextPoint = Point(curPoint.x, curPoint.y - 1)
    }
    else if (curDir == North()) {
      nextPoint = Point(curPoint.x, curPoint.y + 1)
    }

    nextPoint
  }
  def step(): Unit = {
    var appleEaten = false
    curPoint = Point(curPoint.x + curDir.toPoint.x, curPoint.y + curDir.toPoint.y)


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
      applePoint = changeApplePoint()
      appleEaten = true
    }



  }




  def createSnakeBody(curPosition : Point) : CellType = {


    Empty()
  }
  def changeApplePoint(): Point = {
    var nrFreeSpots = 0
    var occupiedSpots = 0

    var freeSpots = ArrayBuffer[Point]()

    for(i <- gridDims.allPointsInside)
      {
        if(i != curPoint)
          {
            freeSpots += i
          }
        if(i == curPoint)
          {
            occupiedSpots += 1
          }
      }


    nrFreeSpots = (gridDims.allPointsInside.size - 1) - occupiedSpots
    val newSpot = freeSpots(random.randomInt(nrFreeSpots))


    newSpot

  }



  def changeDir(d: Direction): Unit = (

    d match {
      case North() => curDir = North()
      case West() => curDir = West()
      case East() => curDir = East()
      case South() => curDir = South()
    }
    )


  def getCellType(p: Point): CellType = (
    if (p == curPoint) {
      SnakeHead(curDir)
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


