package recfun

import scala.annotation.tailrec

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
   if(c == 0 || c == r) 1
   else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */


  def balance(chars: List[Char]): Boolean = {

    @tailrec
    def brackets(str: List[Char], count: Int): Boolean = {
      if (str.isEmpty) count == 0
      else if(str.head == ')' && count == 0) false
      else if (str.head == '(') brackets(str.tail, count + 1)
      else if(str.head == ')') brackets(str.tail, count - 1)
      else brackets(str.tail, count)
    }

    brackets(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(money == 0) 1
    else if(coins.isEmpty || money < 0) 0
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
