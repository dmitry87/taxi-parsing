package ai.cognitiv.taxiparsing.reading.filtration

import ai.cognitiv.taxiparsing.TestBase
import ai.cognitiv.taxiparsing.reading.conversion.{GreenTripRecord, YellowTripRecord}
import ai.cognitiv.taxiparsing.reading.{Readings, TaxiReader}
import org.mockito.Mockito

import java.time.LocalDateTime
import java.util.function.Predicate
import scala.jdk.CollectionConverters._

class FiltratingReaderTest extends TestBase with Readings {

    Feature("Tests filtrations on input Green data") {
        Scenario("Filter Green records") {
            Given("Green records")
            val input = sampleGreenData

            And("filtered on start time")
            val dropAllBefore: Predicate[GreenTripRecord] = new Predicate[GreenTripRecord] {
                override def test(t: GreenTripRecord): Boolean = t.getLpepPickUpDatetime.isAfter(LocalDateTime.parse("2023-10-01T00:00:03.400"))
            }

            And("reader got data")
            val reader: TaxiReader[GreenTripRecord] = mock[TaxiReader[GreenTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllBefore).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 2 records")
            2 shouldBe result.size
        }

        Scenario("Filter on start and end date") {
            Given("Green records")
            val input = sampleGreenData

            And("filtered on start and end time")
            val dropAllBefore: Predicate[GreenTripRecord] = (t: GreenTripRecord) => t.getLpepPickUpDatetime.isAfter(LocalDateTime.parse("2023-10-01T00:00:03.400"))
            val dropAllAfter: Predicate[GreenTripRecord] = (t: GreenTripRecord) => t.getLpepPickUpDatetime.isBefore(LocalDateTime.parse("2023-10-01T00:00:03.600"))

            And("reader got data")
            val reader: TaxiReader[GreenTripRecord] = mock[TaxiReader[GreenTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllBefore, dropAllAfter).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 1 records")
            1 shouldBe result.size
        }

        Scenario("Filter on Locations start and end") {
            Given("Green records")
            val input = sampleGreenData

            And("filtered on start time")
            val dropAllNotStartId: Predicate[GreenTripRecord] = (t: GreenTripRecord) => 74 == t.getPickUpLocationId
            val dropAllNoEndId: Predicate[GreenTripRecord] = (t: GreenTripRecord) => 42 == t.getDropOffLocationId

            And("reader got data")
            val reader: TaxiReader[GreenTripRecord] = mock[TaxiReader[GreenTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllNotStartId, dropAllNoEndId).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 1 records")
            1 shouldBe result.size
        }

        Scenario("Filter on Vendor") {
            Given("Green records")
            val input = sampleGreenData

            And("filtered on vendor")
            val dropAllNotMatchingVendors: Predicate[GreenTripRecord] = (t: GreenTripRecord) => 1 == t.getVendorId

            And("reader got data")
            val reader: TaxiReader[GreenTripRecord] = mock[TaxiReader[GreenTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllNotMatchingVendors).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 1 records")
            0 shouldBe result.size
        }

    }

    Feature("Tests filtrations on input data for Yellow taxis") {
        Scenario("Filter Yellow records") {
            Given("Green records")
            val input = sampleYellowData

            And("filtered on start time")
            val dropAllBefore: Predicate[YellowTripRecord] = new Predicate[YellowTripRecord] {
                override def test(t: YellowTripRecord): Boolean = t.getTpepPickUpDatetime.isAfter(LocalDateTime.parse("2023-10-01T00:00:01.200"))
            }

            And("reader got data")
            val reader: TaxiReader[YellowTripRecord] = mock[TaxiReader[YellowTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllBefore).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 2 records")
            result.size shouldBe 2
        }

        Scenario("Filter on start and end date") {
            Given("Yellow records")
            val input = sampleYellowData

            And("filtered on start and end time")
            val dropAllBefore: Predicate[YellowTripRecord] = (t: YellowTripRecord) => t.getTpepPickUpDatetime.isAfter(LocalDateTime.parse("2023-10-01T00:00:01.400"))
            val dropAllAfter: Predicate[YellowTripRecord] = (t: YellowTripRecord) => t.getTpepPickUpDatetime.isBefore(LocalDateTime.parse("2023-10-01T00:00:01.600"))

            And("reader got data")
            val reader: TaxiReader[YellowTripRecord] = mock[TaxiReader[YellowTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllBefore, dropAllAfter).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 1 records")
            result.size shouldBe 1
        }

        Scenario("Filter on Locations start and end") {
            Given("Yellow records")
            val input = sampleYellowData

            And("filtered on start time")
            val dropAllNotStartId: Predicate[YellowTripRecord] = (t: YellowTripRecord) => 161 == t.getPickUpLocationId
            val dropAllNoEndId: Predicate[YellowTripRecord] = (t: YellowTripRecord) => 186 == t.getDropOffLocationId

            And("reader got data")
            val reader: TaxiReader[YellowTripRecord] = mock[TaxiReader[YellowTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllNotStartId, dropAllNoEndId).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 1 records")
            1 shouldBe result.size
        }

        Scenario("Filter on Vendor") {
            Given("Yellow records")
            val input = sampleYellowData

            And("filtered on vendor")
            val dropAllNotMatchingVendors: Predicate[YellowTripRecord] = (t: YellowTripRecord) => 1 == t.getVendorID

            And("reader got data")
            val reader: TaxiReader[YellowTripRecord] = mock[TaxiReader[YellowTripRecord]]
            Mockito.when(reader.readInput())
                .thenReturn(input.asJava.stream)

            val subject = new FiltratingReader(List(dropAllNotMatchingVendors).asJava, reader)

            When("filtered")
            val result = subject.readInput().toList.asScala

            Then("should contain only 1 records")
            result.size shouldBe 3
        }

    }

}
