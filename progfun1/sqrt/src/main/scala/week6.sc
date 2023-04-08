
val xs = Array(1, 2, 3, 44)
xs map (x => x * 2)

val s = "Hello World"
s.filter(c => c.isUpper)

var r5 = 1 to 5

val r4 = 1 until 4

val n1 = 1 to 10 by 3

val n2 = 6 to 1 by -2

s.exists(_.isUpper)
s.forall(_.isUpper)

val pairs = List(1, 2, 3).zip(s)
pairs.unzip

s.flatMap(c => (List('.', c)))

xs.sum
xs.product
xs.max
xs.min

def scalarProducts(xs: Vector[Double], ys: Vector[Double]): Double = {
  (xs.zip(ys)).map((xy) => xy._1 * xy._2).sum
}

def scalarProducts(xs: Vector[Double], ys: Vector[Double]): Double = {
  (xs.zip(ys)).map { case (x, y) => x * y }.sum
}

def isPrime(n: Int): Boolean = {
  if(n == 1) true
  else if(n == 2) true
  else if(n % 2 == 0) false
  else {
    (2 until n).forall(d => n % d != 0)
  }
}

isPrime(10050659)

