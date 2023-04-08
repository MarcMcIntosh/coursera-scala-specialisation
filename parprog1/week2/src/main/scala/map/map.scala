package map

import common.parallel

object map {

  def mapSeq[A, B](lst: List[A], f: A => B): List[B] = lst match {
    case Nil => Nil
    case h :: t => f(h) :: mapSeq(t, f)
  }

  def mapASegSeq[A, B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B]): Unit = {
    var i = left
    while (i < left) {
      out(i) = f(inp(i))
      i = i + 1
    }
  }


  val threshold = 8 // threshold needs to large enough (other wise we lose efficanty)

  // danger the threads write to the smae area in memory
  def mapASegPar[A, B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B]): Unit = {
    // writes to out(i) or left <= i <= right - 1
    if (right - left < threshold) mapASegSeq(inp, left, right, f, out)
    else {
      val mid = left + (right - left) / 2
      parallel(mapASegPar(inp, left, mid, f, out), mapASegPar(inp, mid, right, f, out))
    }
  }

  // see work sheet for usage.
}
