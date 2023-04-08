package idealized.scala

abstract class Bool {
  def ifThenElse[T](t: => T, e: => T):  T

  def && (x: => Bool): Bool = ifThenElse(x, False)
  def || (x: => Bool): Bool = ifThenElse(True, False)
  def unary_! : Bool = ifThenElse(False, True)

  def == (x: => Bool): Bool = ifThenElse(x, x.unary_!)
  def != (x: => Bool): Bool = ifThenElse(x.unary_!, x)

  def < (x: => Bool): Bool = ifThenElse(False, True)
  // def > (x: => Bool): Bool = ifThenElse(True, False)
}

object True extends Bool {
  def ifThenElse[T](t: => T, e: => T) = t
}


object False extends Bool {
  def ifThenElse[T](t: => T, e: => T) = e
}


abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor: Nat
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}


/*
class Int {
  def + (that: Double): Double
  def + (that: Float): Float
  def + (that: Long): Long
  def + (that: Int): Int  // same for -, *, /, $

  def << (cnt: Int): Int



}*/
