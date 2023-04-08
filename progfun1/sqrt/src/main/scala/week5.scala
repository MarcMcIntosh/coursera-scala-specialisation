

object ls {
  def last[T](xs: List[T]): T = xs match {

    case List() => throw new Error("last of empty list")
    case List(x) => x
    case y :: ys => last(xs.tail)
  }

  def init[T](xs: List[T]): List[T] = xs match {
    case List() => throw new Error("init of empty list")
    case List(x) => List[T]()
    case y :: ys => y :: init(y)
  }

  def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
    case List() => ys
    case z :: zs => z :: concat(zs, ys)
  }

  def reverse[T](xs: List[T]): List[T] = xs match {
    case List() => xs
    case y :: ys => reverse(ys) ++ List(y)
  }
  def removeAt[T](n: Int, xs: List[T]): List[T] = xs.take(n) ::: xs.drop(n + 1)

  def flatten(xs: List[Any]): List[Any] =

  flatten(List(List(1, 1), 2, List(3, List(5, 8))))
}