// sequentual estimate for pi
import scala.util.Random
import common._

def mCount(iter: Int): Int = {
  val randomX = new Random()
  val randomY = new Random()
  var hits = 0

  for (i <- 0 until iter) {
    val x = randomX.nextDouble() // in [0, 1]
    val y = randomY.nextDouble() // in [0, 1]
    if(x*x + y*y < 1) hits = hits + 1
  }

  hits
}

def monteCarloPiSeq(iter: Int): Double = 4.0 * mCount(iter) / iter

def monteCarloPiPara(iter: Int): Double = {
  val (
    (p1, p2),
    (p3, p4)
  ) = parallel(
    parallel(mCount(iter/4), mCount(iter/4)),
    parallel(mCount(iter/4), mCount(iter - 3*(iter/4)))
  )

  4 * (p1 + p2 + p3 + p4) / iter
}