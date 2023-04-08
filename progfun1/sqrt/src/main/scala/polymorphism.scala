package coursera

import scala.annotation.tailrec

trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

class Nil[T] extends List[T] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("NIl.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

// def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])

object nth {
  @tailrec
  def apply[T](n: Int, l: List[T]): T = {
    if (n == 0) l.head
    else if (n < 0) throw new IndexOutOfBoundsException
    else nth(n - 1, l.tail)
  }

  val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))

  nth(2, list)
}