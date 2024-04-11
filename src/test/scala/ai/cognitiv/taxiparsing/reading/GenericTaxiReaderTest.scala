package ai.cognitiv.taxiparsing.reading

import ai.cognitiv.taxiparsing.TestBase
import ai.cognitiv.taxiparsing.reading.conversion.{ConvertersFactory, GreenTripRecord, YellowTripRecord}

import java.io

class GenericTaxiReaderTest extends TestBase with Readings {

    Feature("Reads Parquet files") {

        Scenario("Reader reads yellow taxi file") {
            Given("Yellow taxi file path")
            val path = this.getClass.getResource("/samples/generictaxireader3/yellow_tripdata_sample.parquet/part-00000-42dd3963-c016-4a46-a00e-86f2c46ded86-c000.snappy.parquet").getPath
            val pathArray = new io.File(path)

            And("reader")
            val subject = new GenericTaxiReader(path, new ConvertersFactory, classOf[YellowTripRecord])

            And("Expected to contain")
            val expected: List[YellowTripRecord] = sampleYellowData

            When("File is read")
            val result = subject.readInput().toList

            Then("Should contain expected records")
            result should contain theSameElementsAs (expected)

        }

        Scenario("Reader reads green taxi file") {
            Given("Green taxi file path")
            val path = this.getClass.getResource("/samples/generictaxireader3/green_tripdata_sample.parquet/part-00000-6a5ff549-d0e2-45d5-888c-f6317ea4e667-c000.snappy.parquet").getPath

            And("reader")
            val subject = new GenericTaxiReader(path, new ConvertersFactory, classOf[GreenTripRecord])

            And("Expected to contain")
            val sample = greenSample()
            val expected: List[GreenTripRecord] = sampleGreenData

            When("File is read")
            val result = subject.readInput().toList

            Then("Should contain expected records")
            result should contain theSameElementsAs expected
        }

        ignore("Checks read of a big file") {
            Scenario("Reader reads green big taxi file") {
                Given("Green taxi file path")
                val path = this.getClass.getResource("/samples/bigfile/green_tripdata_2023-10.parquet").getPath

                And("reader")
                val subject = new GenericTaxiReader(path, new ConvertersFactory, classOf[GreenTripRecord])

                And("Expected to contain")
                val sample = greenSample()
                val expected: List[GreenTripRecord] = sampleGreenData

                When("File is read")
                val result = subject.readInput().toList

                Then("Should contain expected records")
                result should contain theSameElementsAs expected
            }
        }


    }

}
