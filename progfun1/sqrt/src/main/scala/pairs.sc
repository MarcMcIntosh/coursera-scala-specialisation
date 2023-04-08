
val n = 7
// (1 until n).map(i => (1 until i).map(j => (i, j))).flatten

def isPrime(n: Int): Boolean = {
  if(n == 1) true
  else if(n == 2) true
  else if(n % 2 == 0) false
  else {
    (2 until n).forall(d => n % d != 0)
  }
}


// (1 until n).flatMap(i => (1 until i).map(j => (i, j)))


/*
case class Person(name: String, age: Int)

for(p <- persons if p.age > 20) yield p.name

persons.filter(p => p.age > 20).map(p => p.name)
*/
/*
for {
  j <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)
*/

def scalarProducts(xs: List[Double], ys: List[Double]): Double = {
  (for {
    (x, y) <- xs.zip(ys)
  } yield x * y).sum
}