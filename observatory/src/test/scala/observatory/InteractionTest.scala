package observatory

import scala.collection.concurrent.TrieMap
import org.junit.Assert._
import org.junit.Test

trait InteractionTest extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("interactive visualization", 3) _

  @Test def `tileLocation test`: Unit = {
    val tile = Tile(0, 0, 0)
    val computed = Interaction.tileLocation(tile)
    val expected = Location(85.05112877980659, -180)

    assert(computed == expected, s"Expected: $expected, recieved: $computed")
  }
}
