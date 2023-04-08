package coursera


// leads on to pattern matching
/*
trait Expr {
  def isNumer: Boolean // classification
  def isSum: Boolean // classification
  def numValue: Int // accessor
  def leftOp: Expr // accessor
  def rightOp: Expr // accessor

  def eval(e: Expr): Int = {
    if(e.isInstanceOf[Number]) e.asInstanceOf[Number].numberValue
  }
}


class Number(n: Int) extends Expr {
  def isNumber: Boolean = true
  def isBoolean: Boolean = false
  def numberValue: Int = n
  def leftOp: Expr = throw new Error("Number.leftOp")
  def rightOp: Expr = throw new Error("Number.rightOp")
}
adding new classes requires allot more code
*/

/*
trait Expr {
  def eval: Int
}

class Number(n: Int) extends Expr {
  def eval: Int = n
}

class Sum(e1: Expr, e2: Expr) extends  Expr {
  def eval: Int = e1.eval + e2.eval
}*/

