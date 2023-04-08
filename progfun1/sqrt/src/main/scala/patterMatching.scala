package week4
// Expr, Number, Sum, Prod,
// Eval, show, simplify,

// sole porpose of test and accessor functions is ro reverse the construction process


trait Expr {
  def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
  }

  def show: String = this match {
    case Number(n) => n.toString
    case Sum(e1, e2) => e1.show + " + " + e2.show
  }
}

/*
def eval(e: Expr): Int = e match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}
 */

case class Number(n: Int) extends Expr
case class Sum(e1: Expr, d2: Expr) extends Expr
/*
def show(e: Expr): String = e match {
  case Number(n) => n.toString
  case Sum(e1, e2) => show(e1) + " + "  + show(e2)
}
*/
object Number {
  def apply(n: Int) = new Number(n)
}

object Sum {
  def apply(e1: Expr, d2: Expr): Sum = new Sum(e1, d2)
}

