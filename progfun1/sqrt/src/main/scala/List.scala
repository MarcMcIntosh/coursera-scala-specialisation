import coursera._

trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail[U >: T]: List[U]
  def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)

  def f(xs: List[NonEmpty], x: Empty): List[IntSet] = xs.prepend.x
}

// Constructor
class Cons[T](val head, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

/*
class Nil[T] extends List[T] {
  def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}
*/

object Nil extends List[Nothing] {
  def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}


object List {
  // List()
  def apply[T]() = new Nil
  // List(1, 2)
  def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, new Nil))
}

object test {
  val x: List[String] = Nil
}

/*
trait Function1[A, B] {
  def apply(x : A): B
}

trait Function[-T, +U] {
  def apply(x: T): U
}

class Array[+T] {
   def apply(x: T)
}


(x: Int) => x * x

{
  class AnonFun extends Function1[Int, Int] {
    def apply(x: Int) = x * x
  }

  new AnonFun
}


val f = (x: Int) => x * x

val f = new Function1[Int, Int] { def apply(x: Int) = x * x }
f.apply(7)

 */