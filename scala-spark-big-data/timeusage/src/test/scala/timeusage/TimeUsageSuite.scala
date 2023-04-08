package timeusage

import org.apache.spark.sql.{ColumnName, DataFrame, Row}
import org.junit.{Assert, Test}
import org.junit.Assert.assertEquals


import scala.util.Random



class TimeUsageSuite {

  val timeUsage = TimeUsage

  val (columns, initDf) = TimeUsage.read("src/test/resources/timeusage/atussum.csv")

  lazy val (primaryNeedsColumns, workColumns, otherColumns) = timeUsage.classifiedColumns(columns)


  @Test def `row`: Unit = {

    val testRow = timeUsage.row(List("id1", "0.314", "10"))

    assert(testRow(0).getClass.getName == "java.lang.String")
    assert(testRow(1).getClass.getName == "java.lang.Double")
    assert(testRow(2).getClass.getName == "java.lang.Double")
  }

  @Test def `classifiedColumns`: Unit = {


    val pnC = primaryNeedsColumns.map(_.toString)
    val wC = workColumns.map(_.toString)
    val oC = otherColumns.map(_.toString)


    assert(pnC.contains("t010199"))
    assert(pnC.contains("t030501"))
    assert(pnC.contains("t110101"))
    assert(pnC.contains("t180382"))
    assert(wC.contains("t050103"))
    assert(wC.contains("t180589"))
    assert(oC.contains("t020101"))
    assert(oC.contains("t180699"))


  }



  lazy val summaryDf: DataFrame = timeUsage.timeUsageSummary(primaryNeedsColumns, workColumns, otherColumns, initDf)

  @Test def `timeUsageSummary`: Unit = {



    assert(summaryDf.columns.length == 6)
    assert(summaryDf.count == 9)

    summaryDf.show()
  }

  lazy val finalDf: DataFrame = timeUsage.timeUsageGrouped(summaryDf)

  @Test def `timeUsageGrouped`: Unit = {

    assert(finalDf.count ==  5)
    assert(finalDf.head.getDouble(3) == 13.1)
    finalDf.show()
  }

  lazy val sqlDf = timeUsage.timeUsageGroupedSql(summaryDf)

  @Test def `timeUsageGroupedSql`: Unit = {
    assert(sqlDf.count == 5)
    assert(sqlDf.head.getDouble(3) == 13.1)
    sqlDf.show()
  }

  lazy val summaryDs = timeUsage.timeUsageSummaryTyped(summaryDf)


  @Test def `timeUsageSummaryTyped`: Unit = {
    assert(summaryDs.head.getClass.getName == "timeusage.TimeUsageRow")
    assert(summaryDs.head.other == 8.75)
    assert(summaryDs.count == 9)
    summaryDs.show()
  }

  lazy val finalDs = timeUsage.timeUsageGroupedTyped(summaryDs)


  @Test def `timeUsageGroupedTyped`: Unit = {
    assert(finalDs.count == 5)
    assert(finalDs.head.primaryNeeds == 13.1)
  }

}
