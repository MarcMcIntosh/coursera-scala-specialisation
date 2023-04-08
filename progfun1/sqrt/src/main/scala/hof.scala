package coursera

import scala.annotation.tailrec

object hof {
  def sum0(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int,  b: Int): Int = {
      if(a > b) 0
      else f(a) + sumF(a + 1, b)
    }
    sumF
  }

  def sum(f: Int => Int)(a: Int, b: Int): Int = if(a > b) 0 else f(a) + sum(f)(a + 1, b)

  // def product(f: Int => Int)(a: Int, b: Int): Int = if(a > b) 1 else f(a) * product(f)(a + 1, b)

  /*
  def product(f: Int => Int)(a: Int, b: Int): Int = {
    @tailrec
    def loop(curr: Int, acc: Int): Int = {
      if (curr > b)
        acc
      else
        loop(curr + 1, acc * f(curr))
    }

    loop(a, 1)
  } */

  def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
    if(a > b) zero
    else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))
  }

  def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x * y, 1)(a, b)


  def sqr(x: Int): Int = x * x

  def fact(n: Int): Int = product(x => x)(1, n)

}
