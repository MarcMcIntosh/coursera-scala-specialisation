
// computing a p-norm
/*
P-norm is a generalization of the notion of length of a vector in geometry.
When p is equal to 2, 2-norm would be simply the length of a vector given by its coordinates.
So we computed by summing up the squares of the coordinates and then raising the result to the power of one to 1/2 [INAUDIBLE].
For arbitrary p, we simply take the sum of the absolute values of the coordinates of the vector raised to the power of p,
and then we raise the resulting sum to the power 1/p. So how would we compute the p-norm of a vector?
 */

def sumSegment(a: Array[Int], p: Double, s:Int, t:Int): Int = {
  var i = s
  var sum: Int = 0

  while(i < t) {
    sum = sum + power(a(i), p)
    i = i + 1
  }

  sum
}

def power(x: Int, p: Double): Int = math.exp(p * math.log(math.abs(x))).toInt

/* sequentual
def pNorm(a: Array[Int], p: Double): Int = {
  val m = a.length / 2
  val (sum1, sum2) = (sumSegment(a, p, 0, m), sumSegment(a, p, m, a.length))
  power(sum1 + sum2, 1/p)
}*/

import common._

def pNorm(a: Array[Int], p: Double): Int = {
  val m = a.length / 2
  val (sum1, sum2) = parallel(sumSegment(a, p, 0, m), sumSegment(a, p, m, a.length))
  power(sum1 + sum2, 1/p)
}

// recursive
def pNormRec(a: Array[Int], p: Double): Int = {
  power(segmentRec(a, p, 0, a.length) 1/p)
}

def segmentRec(a: Array[Int], p: Double, s: Int, t: Int) = {
  if(t - s < threshold) sumSegment(a, p ,s ,t) // small segment do it sequentually
  else {
    val m = s + (t - s) / 2
    val (sum1, sum2) = parallel(segmentRec(a, p, 0, m), segmentRec(a, p, m, t))
    sum1 + sum2
  }
}

def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
  ???
}