import coursera.gcd

import org.junit._
import org.junit.Assert.assertEquals


class gcd {

  @Test def ` Greatest common denominator of 14 and 21 is 7`: Unit = {
    assertEquals(gcd(14, 21), 7)
  }
}
