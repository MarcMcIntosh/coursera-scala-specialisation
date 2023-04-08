package coursera

import scala.annotation.tailrec

/** Tail recursive function **/
object factorial {
  def notTailRecursive(n: Int): Int = if(n == 0) 1 else n * factorial(n - 1)

  def apply(n: Int): Int = {
    @tailrec
    def loop(acc: Int, cur: Int): Int = {
      if(cur == 0) acc
      else loop(acc * cur, cur - 1)
    }

    loop(1, n)
  }
}

