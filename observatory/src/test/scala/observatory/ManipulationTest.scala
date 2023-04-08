package observatory

import org.junit.Test

trait ManipulationTest extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("data manipulation", 4) _

  // Implement tests for methods of the `Manipulation` object

  private val year = 1975
  val stationsPath = "/stations.csv"
  val temperaturePath = s"/test75.csv"

  lazy val locateTemperatures = Extraction.locateTemperatures(year, stationsPath, temperaturePath)
  lazy val locateAverage = Extraction.locationYearlyAverageRecords(locateTemperatures)

  @Test def `makeGrid()`: Unit = {
    val grid = Manipulation.makeGrid(locateAverage)

    val temps = for {
      lat <- -89 to 90
      lon <- -180 to 179
      key = GridLocation(lat, lon)
    } yield grid(key)

    assert(temps.size == 360 * 180)
  }

  @Test def `average()`: Unit = {
    val data = List(List((Location(0.0, 0.0), 10.0)), List((Location(0.2, 0.3), 20.0)))

    val location  = GridLocation(0, 0)
    val average = Manipulation.average(data)

    assert(average(location) == 15)
  }

  @Test def `deviation()`: Unit = {
    val temps = List((Location(0.0, 0.0), 10.0))
    val nomrals: (GridLocation => Temperature) = _ => 0.0

    val deviations = Manipulation.deviation(temps, nomrals)
    val location = GridLocation(0, 0)

    assert(deviations(location) == 10.0)

  }

}
