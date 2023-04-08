import scala.collection.GenSeq
import scala.collection.parallel.CollectionConverters._

/*
def sum(xs: Array[Int]): Int = {
  xs.par.foldLeft(0)((a, b) => a + b)
} */
def sum(xs: Array[Int]): Int = {
  xs.par.fold(0)((a, b) => a + b)
}


def max(xs: Array[Int]): Int = {
  /* xs.par.foldLeft(0)((a: Int, b: Int) => {
    if (a > b) a else b
  })*/
  xs.par.fold(Int.MinValue)(math.max)
}

/* should compile, the accumalator needs the same time as the enties in the the array
def volwels(xs: Array[Char]): Int = {
  xs.par.fold(0)((count, c) => {
    if(isVowel(c)) count + 1 else count
  })
} use aggrigate instead.
 */

  /*

def isVowel(c: Char): Boolean = {
  case 'A' => true
  case 'E' => true
  case 'I' => true
  case 'O' => true
  case 'U' => true
  case _ => false
}

val chars = Array('E', 'P', 'L', 'F')
def vowels(xs: Array[Char]): Int = {
  xs.par.aggregate(0) (
    (count, c) => if(isVowel(c)) count + 1 else count
  , _ + _
  )
}

val d = Array(1,2,3,4,5,6)
sum(d)

max(d)

vowels(chars)
*/
def largestPalindrome(xs: GenSeq[Int]): Int = {
  xs.par.aggregate(Int.MinValue)(
    (largest, n) => if(n > largest && n.toString == n.toString.reverse) n else largest,
    math.max
  )
}

val arr = (0 until 100000).toArray
largestPalindrome(arr)