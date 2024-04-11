package ai.cognitiv.taxiparsing.reporting.aggregation.color

import ai.cognitiv.taxiparsing.TestBase
import ai.cognitiv.taxiparsing.reading.conversion.TaxiColor
import ai.cognitiv.taxiparsing.reading.{AnalyzedTripRecord, TaxiReader}
import ai.cognitiv.taxiparsing.reporting.aggregation.Reportings
import org.mockito.Mockito

import scala.jdk.CollectionConverters._

class ColorGroupingReportTest extends TestBase with Reportings {

    Feature("Groups and aggregates data based on its color") {
        Scenario("A few similar colors were read") {
            Given("Report read and sanitized")
            val groupedDataReader: TaxiReader[AnalyzedTripRecord] = mock[TaxiReader[AnalyzedTripRecord]]
            And("Input")
            val input = List(
                sampleGreenAnalyzedTripRecord,
                sampleGreenAnalyzedTripRecord.toBuilder().pickUpLocationId(67).build(),
                sampleGreenAnalyzedTripRecord.toBuilder().dropOffDateTime(sampleGreenAnalyzedTripRecord.getPickUpDateTime.plusMinutes(5)).fareAmount(6).tollAmount(4).build(),
                sampleGreenAnalyzedTripRecord.toBuilder().color(TaxiColor.YELLOW).build(),
                sampleGreenAnalyzedTripRecord.toBuilder().paymentType(3).build(),
            )
            Mockito.when(groupedDataReader.readInput()).thenReturn(input.asJava.stream())

            And("Expected report")
            val expected = Map(
                TaxiColor.GREEN -> ColorTypeAggregatedTripData.builder()
                    .minFare(1)
                    .maxFare(6)
                    .fareSum(9)
                    .tollsSum(10)
                    .count(4)
                    .taxiColor(TaxiColor.GREEN)
                    .build(),
                TaxiColor.YELLOW -> ColorTypeAggregatedTripData.builder()
                    .minFare(1)
                    .maxFare(1)
                    .fareSum(1)
                    .tollsSum(2)
                    .taxiColor(TaxiColor.YELLOW)
                    .count(1)
                    .build(),
            )

            val subject = new ColorGroupingReport()

            When("report is grouped")
            val actual = subject.prepareGroupedReport(groupedDataReader).asScala

            Then("result should match expectation")
            actual should contain theSameElementsAs (expected)
        }
    }


}
