package calculator

import Math.{pow, sqrt}

object Polynomial {

  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal(pow(b(), 2) - (4 * (a() * c())))
  }
/*
Then, use Δ to compute the set of roots of the polynomial in computeSolutions.
Recall that there can be 0 (when Δ is negative),
1 or 2 roots to such a polynomial,
and that can be computed with the formula: (-b ± √Δ) / 2a

*/
  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal(delta() match {
      case d if d >= 0 => Set((-b() + sqrt(d)) / (2 * a()), (-b() - sqrt(d)) / (2 * a()))
      case d if d == 0 => Set(-b() / (2 * a()))
      case _ => Set()
    })
  }
}
