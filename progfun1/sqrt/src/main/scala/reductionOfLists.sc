def sum(xs: List[Int]): Int = xs match {
  case Nil => 0
  case y :: ys => y + sum(ys)
}

def product(xs: List[Int]): Int = xs match {
  case Nil => 1
  case y :: ys => y + product(ys)
}

def sum(xs: List[Int]) = (0 :: xs).reduceLeft(_ + _)
def product(xs: List[Int]) = (1 :: xs).reduceLeft(_ * _)

def sum(xs: List[Int]) = xs.foldLeft(0)(_ + _)
def product(xs: List[Int]) = xs.foldLeft(1)(_ * _)

def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  (xs foldRight List[U]())(f())

def lengthFun[T](xs: List[T]): Int =
  (xs foldRight 0)(y => y + 1)