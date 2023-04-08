package coursera

import scala.annotation.tailrec

/** Greatest common denominator **/

object gcd {
  // tail recursive function
  @tailrec
  def apply(a: Int, b: Int): Int = {
    if(b == 0) a else gcd(b, a % b)
  }
}
