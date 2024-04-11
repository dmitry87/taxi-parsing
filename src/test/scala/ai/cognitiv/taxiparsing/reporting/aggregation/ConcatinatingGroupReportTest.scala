package ai.cognitiv.taxiparsing.reporting.aggregation

import ai.cognitiv.taxiparsing.TestBase
import ai.cognitiv.taxiparsing.reading.{AnalyzedRecordReader, AnalyzedTripRecord}
import ai.cognitiv.taxiparsing.reporting.aggregation.paymenttype.{PaymentTypeAggregatedTripData, PaymentTypeGroupingReport}
import org.mockito.Mockito

import java.util.stream.{Stream => jStream}
import scala.jdk.CollectionConverters._

class ConcatinatingGroupReportTest extends TestBase {

    Feature("Aggregates a few readers into one report") {
        Scenario("2 yellow and 2 green stream to process") {
            Given("Input data")
            val yellow1 = mock[AnalyzedRecordReader[AnalyzedTripRecord]]
            val yellow2 = mock[AnalyzedRecordReader[AnalyzedTripRecord]]
            val yellowStream = jStream.of(yellow1, yellow2)

            val green3 = mock[AnalyzedRecordReader[AnalyzedTripRecord]]
            val green4 = mock[AnalyzedRecordReader[AnalyzedTripRecord]]
            val greenStream = jStream.of(green3, green4)

            And("Mock setup")
            val groupReportProvider = new PaymentTypeGroupingReport

            val y1List = List[AnalyzedTripRecord](
                AnalyzedTripRecord.builder().paymentType(1D).fareAmount(1D).tollAmount(0D).build()
            ).asJava

            val y2List = List[AnalyzedTripRecord](
                AnalyzedTripRecord.builder().paymentType(1D).fareAmount(2D).tollAmount(1D).build(),
                AnalyzedTripRecord.builder().paymentType(1D).fareAmount(5D).tollAmount(0D).build(),
                AnalyzedTripRecord.builder().paymentType(2D).fareAmount(2D).tollAmount(1D).build(),
                AnalyzedTripRecord.builder().paymentType(2D).fareAmount(5D).tollAmount(0D).build()
            ).asJava

            val g3List = List[AnalyzedTripRecord](
                AnalyzedTripRecord.builder().paymentType(1D).fareAmount(4D).tollAmount(2D).build(),
                AnalyzedTripRecord.builder().paymentType(1D).fareAmount(15D).tollAmount(1D).build(),
                AnalyzedTripRecord.builder().paymentType(2D).fareAmount(1D).tollAmount(1D).build(),
                AnalyzedTripRecord.builder().paymentType(3D).fareAmount(2D).tollAmount(1D).build(),
                AnalyzedTripRecord.builder().paymentType(3D).fareAmount(8D).tollAmount(1D).build(),
            ).asJava

            val g4List = List[AnalyzedTripRecord](
                AnalyzedTripRecord.builder().paymentType(1D).fareAmount(1D).tollAmount(0D).build(),
                AnalyzedTripRecord.builder().paymentType(2D).fareAmount(4D).tollAmount(0D).build(),
                AnalyzedTripRecord.builder().paymentType(2D).fareAmount(9D).tollAmount(0D).build(),
            ).asJava

            Mockito.when(yellow1.readInput()).thenReturn(y1List.stream())
            Mockito.when(yellow2.readInput()).thenReturn(y2List.stream())
            Mockito.when(green3.readInput()).thenReturn(g3List.stream())
            Mockito.when(green4.readInput()).thenReturn(g4List.stream())

            And("Expected")
            val expected = Map(
                1D -> PaymentTypeAggregatedTripData.builder().paymentType(1).minFare(1.0).maxFare(15.0).count(6L).fareSum(28.0).tollsSum(4).build(),
                2D -> PaymentTypeAggregatedTripData.builder().paymentType(2).minFare(1.0).maxFare(9.0).count(5L).fareSum(21.0).tollsSum(2).build(),
                3D -> PaymentTypeAggregatedTripData.builder().paymentType(3).minFare(2.0).maxFare(8.0).count(2L).fareSum(10.0).tollsSum(2).build()
            )


            And("subject")
            val subject = new ConcatinatingGroupReport(groupReportProvider)

            When("Reports read and aggregated")
            val result = subject.readInput(yellowStream, greenStream).asScala

            Then("Should match expectation")
            result should contain theSameElementsAs (expected)


        }
    }

}
