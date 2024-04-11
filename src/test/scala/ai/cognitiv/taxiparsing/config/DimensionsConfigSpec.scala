package ai.cognitiv.taxiparsing.config

import ai.cognitiv.taxiparsing.TestBase
import ai.cognitiv.taxiparsing.comandline.CommandLineArguments

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.jdk.CollectionConverters._

class DimensionsConfigSpec extends TestBase {

    Feature("Converts cli input to config") {
        Scenario("Converts when all elements set") {
            Given("Input")
            val input: CommandLineArguments = new CommandLineArguments
            val files: List[String] = "someFile1,someFile2".split(",").toList
            input.setGreenFilesPaths(files.asJava)
            input.setStartTime(LocalDateTime.parse("2023-09-16T19:56:35", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            input.setEndDateTime(LocalDateTime.parse("2024-09-16T19:56:35", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            input.setGreenTaxi("T")
            input.setYellowTaxi("F")
            input.setVendorId(1)
            input.setPickUpLocationId(168)
            input.setDropOffLocationId(168)
            input.setHelp(false)

            When("converted")
            val actual = new DimensionsConfig(input)

            Then("Match")
            actual.getStart() should be(input.getStartTime)
            actual.getEnd() should be(input.getEndDateTime)
            actual.getPickUpLocationId() should be(input.getPickUpLocationId)
            actual.getDropOffLocationId() should be(input.getDropOffLocationId)
            actual.getVendorId() should be(input.getVendorId)
            actual.isGreenTaxi() shouldBe (true)
            actual.isYellowTaxi() should be(false)
        }

        Scenario("All taxis enabled by default") {
            Given("Input")
            val input: CommandLineArguments = new CommandLineArguments

            When("converted")
            val actual = new DimensionsConfig(input)

            Then("Match")

            actual.isGreenTaxi() should be (true)
            actual.isYellowTaxi() should be(true)
        }

    }

}
