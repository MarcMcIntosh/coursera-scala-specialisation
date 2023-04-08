/*
trait Generator[+T] {
  def generate: T
}

val integers = new Generator[Int] {
  val rand = new java.util.Random
  override def generate = rand.nextInt()
}

val boolean = new Generator[Boolean] {
  override def generate: Boolean = {
    integers.generate > 0
  }
}

val paris = new Generator[(Int, Int)] {
  override def generate: (Int, Int) = {
    (integers.generate, integers.generate)
  }
}
 */

trait Generator[+T] {
  self =>

  def generate: T

  def map[S](f:  T => S): Generator[S] = {
    new Generator[S] {
      override def generate = f(self.generate)
    }
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = {
    new Generator[S] {
      override def generate = f(self.generate).generate
    }
  }
}

val integers = new Generator[Int] {
  val rand = new java.util.Random
  override def generate = rand.nextInt()
}

val booleans = for (x <- integers) yield x > 0

def pairs[T, U](t: Generator[T], u: Generator[U]) = {
  t.flatMap(x => u.map(y => (x, y)))
}

def single[T](x: T): Generator[T] = new Generator[T] {
  override def generate = x
}

def chosse(lo: Int, hi: Int): Generator[Int] = {
  for(x <- integers) yield lo + x % (hi / lo)
}

def oneOf[T](xs: T*): Generator[T] = {
  for(idx <- chosse(0, xs.length)) yield xs(idx)
}

def lists: Generator[List[Int]] = for {
  isEmpty <- booleans
  list <- if(isEmpty) emptyLists else nonEmptyList
} yield list

def emptyLists = single(Nil)
def nonEmptyList = for {
  head <- integers
  tail <- lists
} yield head :: tail

