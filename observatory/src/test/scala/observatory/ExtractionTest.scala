package observatory

import org.junit.Assert._
import org.junit.Test
import java.time.LocalDate

import org.apache.spark.sql.Dataset

trait ExtractionTest extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("data extraction", 1) _

  // Implement tests for the methods of the `Extraction` object

  val year = 1975
  val debug = true
  @Test def `locateTemperatures Empty stations and Empty temperatures`: Unit = {

    val year = 0
    val stations = Extraction.spark.sparkContext.parallelize(List(""))
    val temperatures = Extraction.spark.sparkContext.parallelize(List(""))

    val computed = Extraction.sparkLocateTemperatures(year, stations, temperatures).collect()
    val expected = Array[(LocalDate, Location, Temperature)]()

    assert(computed.sameElements(expected))

  }

  @Test def `locateTemperatures Empty stations and stub temperatures`: Unit = {

    val year = 0
    val stations = Extraction.spark.sparkContext.parallelize(List(""))
    val temperatures = Extraction.spark.sparkContext.parallelize(List("010010,,01,01,23.2"))


    val computed = Extraction.sparkLocateTemperatures(year, stations, temperatures).collect()
    val expected = Array[(LocalDate, Location, Temperature)]()

    assert(computed.sameElements(expected))

  }

  @Test def `LocalTepratesures stub stations and stub tempratures`: Unit = {
    val year = 0
    val stations = Extraction.spark.sparkContext.parallelize(List("4,,+1,+2", "4,5,+1,+2"))
    val temperatures = Extraction.spark.sparkContext.parallelize(List("4,,01,01,32", "4,10,01,01,32"))

    val result = Extraction.sparkLocateTemperatures(year, stations, temperatures).collect
    val expected = Array((LocalDate.of(year, 1, 1), Location(1, 2), 0))

    assert(result.sameElements(expected))
  }



  @Test def `LocalTepratesuresYeallyAverage stub stations and stub tempratures`: Unit =  {
    val records = Extraction.spark.sparkContext.parallelize(List[(LocalDate, Location, Temperature)]())

    val results = Extraction.sparkLocationYearlyAverageRecords(records).collect()
    val expected = Array[(Location, Temperature)]()

    assert(results.sameElements(expected))
  }

  @Test def `locationYearlyAverageRecords stub both`: Unit = {
    val year = 4
    val records = Extraction.spark.sparkContext.parallelize(
      List[(LocalDate, Location, Temperature)]
        ((LocalDate.of(year, 1, 1), Location(1, 2), 4),
          (LocalDate.of(year, 4, 1), Location(1, 2), 5),
          (LocalDate.of(year, 1, 5), Location(1, 2), 6),
          (LocalDate.of(year, 1, 1), Location(2, 2), 4)))

    val results = Extraction.sparkLocationYearlyAverageRecords(records).collect().toSet
    val expected = Set[(Location, Temperature)]((Location(1, 2), 5), (Location(2, 2), 4))

    assert(results == expected)
  }



  /* @Test def `runing`: Unit = {
    val localTempratues = Extraction.sparkLocateTemperatures(1975, "/stations.csv", "/1975.csv").collect()

    localTempratues.foreach(println)

    assert(localTempratues.length > 0, "Wrong size")
  } */
/*
  val stationsPath:String = "/stations.csv"
  val temperaturePath:String = s"/$year.csv"


  lazy val stations: Dataset[Station] = Extraction.stations(stationsPath).persist
  lazy val temperatures: Dataset[TemperatureRecord] = Extraction.temperatures(year, temperaturePath).persist
  lazy val joined: Dataset[JoinedFormat] = Extraction.joined(stations, temperatures).persist

  lazy val locateTemperatures = Extraction.locateTemperatures(year, stationsPath, temperaturePath)
  lazy val locateAverage = Extraction.locationYearlyAverageRecords(locateTemperatures)


  @Test def `Extraction stations`: Unit = {
    if(debug) stations.show()

    assert(stations.filter((station:Station) => station.id=="007005").count() == 0,"id: 007005")

    assert(stations.filter((station:Station) => station.id=="007018").count() == 0,"id: 007018")

    assert(stations.filter((station:Station) => station.id=="725346~94866").count() == 1,"id: 725346~94866")

    assert(stations.filter((station:Station) => station.id=="725346").count() == 1,"id: 725346")

    assert(stations.filter((station:Station) => station.id=="~68601").count() == 1,"id: ~68601")

    assert(stations.count() == 27708, "Num stations")
  }

  @Test def `Extraction.temperatures`: Unit = {
    if(debug) temperatures.show()
    assert(temperatures.filter((tr:TemperatureRecord) => tr.id=="010010").count() == 363,"id: 010010")
    assert(temperatures.filter((tr:TemperatureRecord) => tr.id=="010010" && tr.day ==1 && tr.month == 1 && tr.temperature == (23.2-32)/9*5).count() == 1,"id: 010010")
  }

  @Test def `Exrtaction.joined`: Unit = {
    if(debug) joined.show()
    assert(joined.filter((jf:JoinedFormat) => jf.date == StationDate(1,1,1975) && jf.location==Location(70.933,-008.667)).count() == 1,"id: 010010 ")
    assert(joined.filter((jf:JoinedFormat) => jf.date == StationDate(1,1,1975) && jf.location==Location(70.933,-008.666)).count() == 0,"no loc ")
  }

  @Test def `Extraction.locateTemperatures`: Unit = {
    if(debug) locateTemperatures.take(20).foreach(println)
    assert(locateTemperatures.count(_._2  == Location(70.933,-8.667)) == 363)
    assert(locateTemperatures.size == 2176493)
  }

  @Test def `Extraction.locationYearlyAverageRecords`: Unit = {
    if(debug) locateAverage.take(20).foreach(println)
    assert(locateAverage.count(_._1 == Location(70.933, -8.667)) == 1)
    assert(locateAverage.size == 8251)
  }
*/

}
