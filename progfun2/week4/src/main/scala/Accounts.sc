import accounts._

object observers {
  val a = new BankAccount
  val b = new BankAccount
  val c = new Consolidator(List(a, b))
  c.totalBalance
}