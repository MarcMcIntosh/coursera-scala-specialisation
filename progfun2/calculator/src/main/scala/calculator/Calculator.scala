package calculator

import scala.annotation.tailrec

sealed abstract class Expr
final case class Literal(v: Double) extends Expr
final case class Ref(name: String) extends Expr
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(
      namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions.map {
      case (key, value) => {
        key -> Signal(eval(value(), namedExpressions))
      }
    }
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = {

    def get(n: Expr) = eval(n , references)

    expr match {
      case Literal(n) => n
      case Plus(a, b) => get(a) + get(b)
      case Minus(a, b) => get(a) - get(b)
      case Times(a, b) => get(a) * get(b)
      case Divide(a, b) => get(a) / get(b)
      case Ref(name) => eval(getReferenceExpr(name, references), references - name)
    }
  }

  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
      references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}
