
val nums = List(2, -4, 5, 7, 1)
val fruits = List("apple", "pineapple", "oragne", "banana")

nums.filter(_ > 0)
nums.filterNot(_ > 0)
nums.partition(_ > 0)
nums.takeWhile(_ > 0)
nums.dropWhile(_ > 0)
nums.span(_ > 0)


val toPack = List("a", "a", "a", "b", "c", "c", "a")

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (first, second) = xs1.span(_ == x)
    first :: pack(second)
}

pack(toPack)

/*
def runLength[T](xs: List[T]): List[(T, Int)] = xs match {
  case Nil => Nil
  case y :: ys =>
    val (first, sec) = ys.span(_ == y)
    (first.head, first.length) :: pack(sec)
}*/

def encode[T](xs: List[T]): List[(T, Int)] =
  pack(xs).map(ys => (ys.head, ys.size))

encode(toPack)