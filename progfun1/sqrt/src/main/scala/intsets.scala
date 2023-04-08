package coursera

object insets {
  val t1 = new NonEmpty(3, new Empty, new Empty)
  val t2 = t1.incl(4)
}


abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}


class Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  override def toString = "."
  def union(other: IntSet): IntSet = other
}


class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  override def contains(x: Int): Boolean = {
    if (x < elem) this.left.contains(x)
    else if (x > elem ) this.right.contains(x)
    else true
  }

  def incl(x: Int): IntSet = {
    if (x < elem) new NonEmpty(elem, this.left.incl(x), this.right)
    else if (x > elem) new NonEmpty(elem, this.left, this.right.incl(x))
    else this
  }

  override def toString = "{" + this.left + this.elem + this.right + "}"

  override def union(other: IntSet): IntSet = this.left.union(this.right.union(other)).incl(elem)
}