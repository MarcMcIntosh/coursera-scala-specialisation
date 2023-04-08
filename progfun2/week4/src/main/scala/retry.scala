import scala.annotation.tailrec
import scala.concurrent.Future


/* Use with something that is a future
def fallbackTo(that: => Future[T]): Future[T] = {
  this.recoverWith { case _ => that.recoverWith { case _ this }
}

 */
@tailrec
def retry(noTimes: Int)(block: => Future[T]): Future[T] ={
  if(noTimes == 0) Future.failed(new Exception("sorry"))
  else {
    block.fallbackTo {
      retry(noTimes - 1) { block }
    }
  }
}

// without recursion foldLeft
def retry2(noTimes: Int)(block: => Future[T]): Future[T] = {
  val ns = (1 to noTimes).toList
  val attempts = ns.map(_ => () => block)
  val failed = Future.failed(new Exception("Boom"))
  val result = attempts.foldLeft(failed) {(a, f) => a.recoverWith { f() }}
  result
}
// foldRight
def retry2(noTimes: Int)(block: => Future[T]): Future[T] = {
  val ns = (1 to noTimes).toList
  val attempts = ns.map(_ => () => block)
  val failed = Future.failed(new Exception("Boom"))
  val result = attempts.foldRight(() => failed) {
    (f, a) => () => f().fallbackTo {
      a()
    }
  }
  result()
}

}