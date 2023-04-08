import common._

def parMergeSort(xs: Array[Int], maxDepth: Int): Unit = {
  val ys = new Array[Int](xs.length)

  // @tailrec
  def sort(from: Int, until: Int, depth: Int): Unit = {
    if(depth == maxDepth) {
      // sequence
      quickSort(xs, from, until - from)
    } else {
      val mid = (from + until) / 2
      parallel(sort(mid, until, depth + 1), sort(from, mid, depth + 1))

      val flip = (maxDepth - depth) % 2 == 0
      val src = if (flip) ys else xs
      val dst = if (flip) xs else ys
      merge(src, dst, from, mid, until)
    }
  }


  // seqence
  def merge(src: Array[Int], dst: Array[Int], from: Int, mid: Int, until: Int): Unit = {
    var left = from
    var right = mid
    var i = from

    while(left < mid && right < until) {
      while (left < mid && src(left) <= src(right)) { dst(i) = src(left); i += 1; left += 1 }
      while (right < until && src(right) <= src(left)) { dst(i) = src(right); i +=1; right += 1 }
    }
    while (left < mid) { dst(i) = src(left); i += 1; left += 1 }
    while (right < until) { dst(i) = src(right); i += 1; right += 1}
  }

  sort(0, xs.length, 0)


  def copy(src: Array[Int], target: Array[Int], from: Int, until: Int, depth: Int): Unit = {
    if(depth == maxDepth) Array.copy(src, from, target, from, until - from)
    else {
      val mid =(from + until) / 2
      val right = parallel(
        copy(src, target, mid, until, depth + 1),
        copy(src, target, from, mid, depth + 1)
      )
    }
  }
  if (maxDepth % 2 == 0) copy(ys, xs, 0, xs.length, 0)
}


