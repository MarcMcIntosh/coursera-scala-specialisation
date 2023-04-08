/*
def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last(xs.tail)
}

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init of empty list")
  case List(x) => List[T]
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

def flatten(xs: List[Any]): List[Any] = xs match {
  case List() => xs
  case y :: ys => y ++ flatten(ys)
}

flatten(List(List(1, 1), 2, List(3, List(5, 8))))
*/

/*
def msort(xs: List[Int]): List[Int] = {
  val n: Int = xs.length / 2
  if(n == 0) xs
  else {
    def merge(xs: List[Int], ys: List[Int]): List[Int] = xs match {
      case Nil => ys
      case x :: xs1 => ys match {
        case Nil => xs
        case y :: ys1 => {
          if(x < y) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
        }
      }
    }
    val (fst, snd) = xs.splitAt(n)
    merge(msort(fst), msort(snd))
  }
}*/

/*
def msort(xs: List[Int]): List[Int] = {
  val n: Int = xs.length / 2
  if(n == 0) xs
  else {
    def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) => if(x < y) {
        x :: merge(xs1, ys)
      } else {
        y :: merge(xs, ys1)
      }
    }
    val (fst, snd) = xs.splitAt(n)

    merge(msort(fst), msort(snd))
  }
}*/

def msort[T](xs: List[T])(cmpr: (T, T) => Boolean): List[T] = {
  val n: Int = xs.length / 2
  if(n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) => if(cmpr(x, y)) {
        x :: merge(xs1, ys)
      } else {
        y :: merge(xs, ys1)
      }
    }
    val (fst, snd) = xs.splitAt(n)

    merge(msort(fst)(cmpr), msort(snd)(cmpr))
  }
}



msort(List(2, 1, 6))((x: Int, y: Int) => x < y)

msort(List('a', 'd', 'c', 'e', 'b'))((x: Char, y: Char) => x < y)

val fruits = List("apple", "pineapple", "oragne", "banana")

msort(fruits)((x: String, y: String) => x.compareTo(y) < 0)

// why (val)(func) is better that (val, func)
// because the compiler can infer the type func should take from the type of a


// ordering
def msort[T](xs: List[T])(implicit ord: [math.Ordering[T]]): List[T] = {
  val n: Int = xs.length / 2
  if(n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) => {
        if(ord.lt(x, y)) {
          x :: merge(xs1, ys)
        } else {
          y :: merge(xs, ys1)
        }
      }
    }
    val (fst, snd) = xs.splitAt(n)

    merge(msort(fst), msort(snd))
  }
}

msort(List(2, 1, 6))(Ordering.Int)

msort(List('a', 'd', 'c', 'e', 'b'))(Ordering.String)

// compiler can figure it out
msort(List(2, 1, 6))
msort(List('a', 'd', 'c', 'e', 'b'))
// msort(fruits)((x: String, y: String) => x.compareTo(y) < 0)

/*
def scaleList(cs: List[Double], factor: Double): List[Double] = xs match {
  case Nil => xs
  case y :: ys => y * factor :: scaleList(ys, factor)
}*/
/*
abstract class List[T] {
  def map[U](f: T => U): List[U]= this match {
    case Nil => this
    case x :: xs => f(x) :: xs.map(f)
  }
}*/

def scaleList(xs: List[Double], factor: Double) =
  xs.map(x => x * factor)


def squareList(xs: List[Int]): List[Int] => xs match {
  case Nil => Nil
  case y :: ys => y * y :: squareList(ys)
}

def squareList(xs: List[Int]): List[Int] => xs.map(_ * _)

def posElems(xs: List[Int]): List[Int] => xs match {
  case Nil => Nil
  case y :: ys => if(y >= 0) y :: posElem(ys) else posElem(ys)
}

def posElems(xs: List[Int]): List[Int] => xs.filter(_ >= 0)


def filter(p: T => Boolean): List[T] = this match {
  case Nil => Nill
  case x :: xs => if(p(x)) x :: filter(xs) else filter(xs)
}