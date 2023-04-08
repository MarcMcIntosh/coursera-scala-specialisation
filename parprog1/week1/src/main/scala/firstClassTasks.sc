import common._

/*
val (v1, v2) = parallel(e1, e2)
could be writen as


val t1 = task(e1)
val t2 = task(e2)
val v1 = t1.join()
val v2 = t2.join()

using background tasks

minimal interface for Task: see common for a better one

def task(c: => A) : Task[A]

trait Task[A] {
  def join: A
}

save calls to join with
implicit def getJoin[T](x: Task[T]): T = x.join

this saves nexted call to parallel

val t1 = task { sumSegment(a, p, 0, m1) }
val t2 = task { sumSegment(a, p, mid1, mid2) }
val t3 = task { sumSegment(a, p, mid2, mid3) }
val t4 = task { sumSegment(a, p, mid3, a.length) }
power(t1 + t2 + t3 + t4, 1/p )


try and use task to impliment paralel

def parallel[A, B](cA: => A, cB: => B): (A, B) => {
  val tB: Task[B] = task { cB }
  val tA: A = cA
  // join is called last
  (tA, tB.join())
}
*/