// continued from HelloThread

object foo {
  private var uidCount = 0L

  def getUinqueUid(): Long = {
    uidCount = uidCount + 1
    uidCount
  }
}

class Account(private var amount: Int = 0) {

  val uid = foo.getUinqueUid()

  def getAmount() = {
    amount
  }

  private def lockAndTransfer(target: Account, n: Int) = {
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }
  }

  def transfer(target: Account, n: Int) = {
    if(this.uid < target.uid) this.lockAndTransfer(target, n)
    else target.lockAndTransfer(this, -n)
  }

  override def toString: String = {
    s"acount: $uid\t ammount: $amount"
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

  def toString: String = {
    s"${a.uid}: ${a.getAmount()}\n${b.uid}: ${b.getAmount()}"
  }

  t.start()
  t
}

val a1 = new Account(50000)
val a2 = new Account(70000)

val t3 = startThread(a1, a2, 150000)
val t4 = startThread(a2, a1, 150000)
a1.getAmount()
a2.getAmount()

/// some rules for syncornisation
// 1. two threads writing to diffrent locations in memory do not need syncronisation
// 2. A thread X that calls join on another thread Y
// is guaranteed to observe all the writes by thread Y
// after join returns