package map

import common._

sealed abstract class Tree[A] { val size: Int }

case class Leaf[A](a: Array[A]) extends Tree[A] {
  override val size = a.size
}

case class Node[A](l: Tree[A], r: Tree[A]) extends Tree[A] {
  override val size: Int = l.size + r.size
}

// assume trees are balanced: we can explore branches in parallel

object mapTreePar {
  def apply[A:Manifest, B:Manifest](t: Tree[A], f: A => B): Tree[B] = t match {
    case Leaf(a) => {
      val len = a.length
      val b = new Array[B](len)
      var i = 0
      while (i < len) { b(i) = f(a(i)); i = i + 1; }
      Leaf(b)
    }
    case Node(l, r) => {
      val (lb, rb) = parallel(mapTreePar(l, f), mapTreePar(r, f))
      Node(lb, rb)
    }
  }
}

// Immutable trees can be purely functional, better than arrays
// arrays have good memory locality