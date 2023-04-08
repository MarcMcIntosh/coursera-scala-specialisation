
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
  }

  sum
}




def power(x: Int, p: Double): Int = math.exp(p * math.log(math.abs(x))).toInt

def pNorm(a: Array[Int], p: Double): Int = {
  power(sumSegment(a, p, 0, a.length), 1/p)
}