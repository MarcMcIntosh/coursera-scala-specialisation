package coursera

import scala.annotation.tailrec

object sum {

  def spec(f: Int => Int)(a: Int, b: Int): Int = {
    if(a > b) 0 else f(a) + spec(f)(a + 1, b)
  }

  def apply(f: Int => Int)(a: Int, b: Int): Int = {

    @tailrec
    def loop(a: Int, acc: Int): Int = {
      if(a > b) acc
      else loop(a + 1, acc + f(a))
    }

    loop(a, 0)
  }

  def sum(f: Int => Int)(a: Int, b: Int): Int = {

    @tailrec
    def loop(a: Int, acc: Int): Int = {
      if(a > b) acc
      else loop(a + 1, acc + f(a))
    }

    loop(a, 0)
  }


  def id(x: Int): Int = x
  def cube(x: Int): Int = x * x * x
  def fact(x: Int): Int = if (x == 0) 1 else fact(x - 1)
}
