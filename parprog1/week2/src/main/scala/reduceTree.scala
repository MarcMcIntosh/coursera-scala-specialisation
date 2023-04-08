package reduceTree

import map._
import common._

object reduceTree {
  def apply[A](t: Tree[A], f: (A, A) => A): A = t match {
    case Leaf(v) => v
    // case Node(l, r) => f(reduceTree[A](l, f), reduceTree[A](r, f))
    case Node(l, r) => {
      val (lv, rv) = parallel(reduceTree(lv, f), reduceTree(rv, f))
      f(lv, rv)
    }
  }

  val threshold = 8

  def reduceSegment[A](inp: Array[A], left: Int, right: Int, f: (A, A) => A): A = {
    if(right - left < threshold) {
      var res = inp(left)
      var i = left + 1
      while (i < right) { res = f(res, inp(i)); i = i + 1}
      res
    } else {
      val mid = left + (right - left) / 2
      val (a1, a2) = parallel(reduceSegment(inp, left, mid, f), reduceSegment(inp, mid, right, f))
      f(a1, a2)
    }
  }

  def apply[A](inp: Array[A], f: (A, A) => A): A = {
    reduceSegment(inp, 0, inp.length, f)
  }
}

