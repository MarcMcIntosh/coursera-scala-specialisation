package observatory

import java.time.LocalDate

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.io.Source



/**
  * 1st milestone: data extraction
  */
object Extraction extends ExtractionInterface {

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  implicit val spark: SparkSession = SparkSession
    .builder()
    .master("local")
    .appName("observatory")
    .getOrCreate()


  def getRDDFromResource(resource: String): RDD[String] = {
    val fileStream = Source.getClass.getResourceAsStream(resource)
    spark.sparkContext.makeRDD(Source.fromInputStream(fileStream).getLines().toList)
  }

  def fahrenheitToCelsius(fahrenheit: Temperature): Temperature = (fahrenheit - 32) / 1.8


  def sparkLocateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): RDD[(LocalDate, Location, Temperature)] = {
    val stationsRaw = getRDDFromResource(stationsFile)
    val temperaturesRaw = getRDDFromResource(temperaturesFile)

    sparkLocateTemperatures(year, stationsRaw, temperaturesRaw)
  }

  def sparkLocateTemperatures(year: Year, stationsRaw: RDD[String],
                              temperaturesRaw: RDD[String]): RDD[(LocalDate, Location, Temperature)] = {

    val stations = stationsRaw
      .map(_.split(','))
      .filter(_.length == 4)
      .map(a => ((a(0), a(1)), Location(a(2).toDouble, a(3).toDouble)))

    val temperatures = temperaturesRaw
      .map(_.split(','))
      .filter(_.length == 5)
      .map(a => ((a(0), a(1)), (LocalDate.of(year, a(2).toInt, a(3).toInt), fahrenheitToCelsius(a(4).toDouble))))

    stations.join(temperatures).mapValues(v => (v._2._1, v._1, v._2._2)).values
  }

  def sparkLocationYearlyAverageRecords(records: RDD[(LocalDate, Location, Temperature)]): RDD[(Location, Temperature)] = {
    records
      .groupBy(_._2)
      .mapValues(entries => entries.map(entry => (entry._3, 1)))
      .mapValues(_.reduce((v1, v2) => (v1._1 + v2._1, v1._2 + v2._2)))
      .mapValues({case (temp, cnt) => temp / cnt})
  }

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {
    sparkLocateTemperatures(year, stationsFile, temperaturesFile).collect.toSeq
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    sparkLocationYearlyAverageRecords(spark.sparkContext.parallelize(records.toSeq)).collect().toSeq
  }

}
