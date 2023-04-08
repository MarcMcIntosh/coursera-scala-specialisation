
// List(x, xs) = x :: xs
// x :: xs equivalent to new Cons(x, xs)
// A :: B :: C eqivalent to A :: (B :: C)

// val nums = 1 :: 2 :: 3 :: 4 :: Nil
// compies to Nil.::(4).::(3).::(2).::(1)

// all operations on lists can be expressed in terms of three operations
// head, tale and isEmpty


object isort {
  def apply(xs: List[Int]) = xs match {
    case List() => List()
    case y :: ys => insert(y, isort(ys))
  }

  def insert(y: Int, ys: List[Int]): List[Int] = ys match {
    case List() => List(y)
    case z :: zs  => if(y < z) z :: zs else z :: insert(z, zs)
  }
}
