package coursera

/*
object rationals {

  // def addRational(r: Rational, s: Rational): Rational = ???
  val x = new Rational(1, 2)
  val y = new Rational(2, 3)
  val z = new Rational(3, 2)

  x.add(y) // Rational = 22/21
  x.sub(y).sub(z)  // Rational -79.42
  y.add(y)  // true
  x.max(y) // 5/
  new Rational(2) // 2/1
}*/

class Rational (x: Int, y: Int) {
  require(y != 0, "denominator must non-zero")

  // second class constructor
  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  private val g = gcd(x, y)

  def numer = x / g
  def denom = y / g

  // def numer = x
  // def denom = y


  def < (that: Rational): Boolean = this.numer * that.denom < that.numer * this.denom

  def max(that: Rational): Rational = if(this < that) that else this

  def + (that: Rational) = new Rational(this.numer * that.denom + that.numer * this.denom, this.denom * that.denom)

  def neg: Rational = new Rational(-this.numer, this.denom)

  def - (that: Rational) = this + that.neg

   /* override def toString = {
    val g = gcd(x, y)
    numer / g  + "/" + denom / g

    a + b ^? c ?^ d less a ==> b | c
    ((a + b) ^? (c ?^ d)) less ((a ==> b) | c)
  } */

  override def toString = numer + "/" + denom


}

