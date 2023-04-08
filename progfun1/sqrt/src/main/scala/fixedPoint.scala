package coursera

import math.abs
import scala.annotation.tailrec

object fixedPoint {

  def adverageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

  def apply(f: Double => Double)(firstGuess: Double) = {

    val tolerance = 1e-4

    def isCloseEnough(x: Double, y: Double) = abs((x - y) / x) / x < tolerance

    @tailrec
    def iterate(guess: Double): Double = {
      val next = f(guess)
      if(isCloseEnough(guess, next)) next
      else iterate(next)
    }
    iterate(firstGuess)
  }

  // def sqrt(x: Double) = fixedPoint(y => x / y)(1.0)
  // def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1.0)

  def sqrt(x: Double) = fixedPoint(adverageDamp(y => x / y))(1.0)

}
