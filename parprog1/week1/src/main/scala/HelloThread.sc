

class HelloThread extends Thread {
  override def run(): Unit = {
    println("Hello World!")
  }
}

val t = new HelloThread

// start
t.start()

// wait for conclusion called by main thread
// blocks main thread.
t.join()


class HelloThread2 extends Thread {
  override def run(): Unit = {
    println("Hello ")
    println("World!")
  }
}

def main(): Unit = {
  val t1 = new HelloThread2
  val t2 = new HelloThread2
  t1.start()
  t2.start()
  t1.join()
  t2.join()
}
//  can over lap
main(); main(); main()

// atomic = appears if the process happend instantanoulsy from the view pioint of other threads

object foo {
  private var uidCount = 0L

  def getUinqueUid(): Long = {
    uidCount = uidCount + 1
    uidCount
  }

  def startThread() = {
    val t = new Thread {
      override def run(): Unit = {
        val uids = for (i <- 0 until 10) yield getUinqueUid()
        println(uids)
      }
    }

    t.start()
    t
  }
  // shouldn't be unique
  startThread(); startThread();
}
foo

object fooBar {
  // should sync
  private var x = new AnyRef {}
  private var uidCount = 0L

  def getUinqueUid(): Long = x.synchronized {
    uidCount = uidCount + 1
    uidCount
  }

  def startThread() = {
    val t = new Thread {
      override def run(): Unit = {
        val uids = for (i <- 0 until 10) yield getUinqueUid()
        println(uids)
      }
    }

    t.start()
    t
  }
  // shouldn't be unique
  startThread(); startThread();
}

fooBar

// nesting syncronisations
class Account(private var amount: Int = 0) {
  def transfer(target: Account, n: Int) = {
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }
  }
}

// no gloabl sync
def startThread(a: Account, b: Account, n: Int) = {
  val t = new Thread {
    override def run() = {
      for (i <- 0 until n) {
        a.transfer(b, 1)
      }
    }
  }
  t.start()
  t
}

val a1 = new Account(50000)
val a2 = new Account(70000)

val t3 = startThread(a1, a2, 150000)
val t4 = startThread(a2, a1, 150000)
// blocks waiting on first join
// because the threads are competeing for resourses
// deadlock!
t3.join()
t4.join()
// to avoid this aquire resourses in the same order
// this can be done by having uniquIds on each account
// and having a lock
