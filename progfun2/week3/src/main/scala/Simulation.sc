import com.sun.imageio.plugins.wbmp.WBMPImageReader

import scala.annotation.tailrec

type Action = () => Unit

trait Simulation {
  case class Event(time: Int, action: Action)
  private type Agenda = List[Event]
  private var agenda: Agenda = List()

  private var curtime = 0
  def currentTime: Int = curtime

  def afterDelay(delay: Int)(block: => Unit): Unit = {
    val item = Event(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }

  private def insert(ag: Agenda, item: Event): Agenda = ag match {
    case first :: rest if first.time <= item.time => first :: insert(rest, item)
    case _ => item :: ag
  }

  @tailrec
  private def loop(): Unit = agenda match {
    case first :: rest => {
      agenda = rest
      curtime = first.time
      first.action()
      loop()
    }
    case Nil =>
  }

  def run(): Unit = {
    afterDelay(0) {
      println(s"*** simulation has started, time = ${currentTime} ***")
    }
    loop()
  }

}

trait Parameters {
  def InverterDelay = 2
  def AndGateDelay = 3
  def OrGateDelay = 5
}

class Wire {
  private var sigVal = false
  private var actions: List[Action] = List()

  def getSignal = sigVal

  def setSignal(s: Boolean) = {
    if(s != sigVal) {
      sigVal = s
      actions.foreach(_())
    }
  }

  def addAction(a: Action) = {
    actions = a :: actions
    a()
  }
}

abstract class Gates extends Simulation {
  def InverterDelay = Int
  def AndGateDelay = Int
  def OrGateDelay = Int

  def inverter(input: Wire, output: Wire): Unit = {
    def invertAction(): Unit = {
      val inputSignal = input.getSignal
      afterDelay(InverterDelay) {
        output.setSignal(!inputSignal)
      }
    }
    input.addAction(invertAction)
  }

  def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def andAction() = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(AndGateDelay) {
        output.setSignal(in1Sig & in2Sig)
      }
    }
    in1.addAction(andAction)
    in2.addAction(andAction)
  }

  def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction() = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(OrGateDelay) {
        output.setSignal(in1Sig & in2Sig)
      }
    }
    in1.addAction(orAction)
    in2.addAction(orAction)
  }

  def probe(name: String, wire: Wire): Unit = {
    def probeAction(): Unit = {
      println(s"$name $currentTime value = ${wire.getSignal}")
    }

    wire.addAction(probeAction)
  }
}

abstract class Circuts extends Gates {

  def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) = {
    val d, e = new Wire
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)
  }

  def fullAdder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire): Unit = {
    val s, c1, c2 = new Wire
    halfAdder(a, cin, s, c1)
    halfAdder(b, s, sum, c2)
    orGate(c1, c2, cout)
  }
}

object sim extends Circuts with Parameters
