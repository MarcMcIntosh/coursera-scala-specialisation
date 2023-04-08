package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 1000 // 100000000
    val chars = new Array[Char](length)
    val threshold = 10 // 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {

    @tailrec
    def brackets(str: Array[Char], count: Int): Boolean = {
      if(str.isEmpty) count == 0
      else if (str.head == ')' && count == 0) false
      else if (str.head == ')') brackets(str.tail, count - 1)
      else if (str.head == '(') brackets(str.tail, count + 1)
      else brackets(str.tail, count)
    }

    brackets(chars, 0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    @tailrec
    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int, Int) = {
      if(idx >= until) (arg1, arg2)
      else chars(idx) match {
        case '(' => traverse(idx + 1, until, arg1 + 1, arg2)
        case ')' => if (arg1 > 0) traverse(idx + 1, until, arg1 - 1, arg2) else traverse(idx + 1, until, arg1, arg2 + 1)
        case _ => traverse(idx + 1, until, arg1, arg2)
      }
    }


    def reduce(from: Int, until: Int): (Int, Int) = {
      val size = until - from
      if(threshold >= size) traverse(from , until, 0, 0)
      else {
        val mid = from + (size / 2)
        val ((leftOpen, leftClose), (rightOpen, rightClose)) = parallel(
          reduce(from, mid),
          reduce(mid, until)
        )

        val open = if(leftOpen > rightClose) leftOpen - rightClose + rightOpen else rightOpen
        val close = if(leftOpen > rightClose) leftClose else rightClose - leftOpen + leftClose

        (open, close)
      }
    }


    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
