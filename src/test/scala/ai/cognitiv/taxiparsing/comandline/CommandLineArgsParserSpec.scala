package ai.cognitiv.taxiparsing.comandline

import ai.cognitiv.taxiparsing.TaxiTripsReportException
import com.beust.jcommander.ParameterException
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.jdk.CollectionConverters._

class CommandLineArgsParserSpec extends AnyFeatureSpec with GivenWhenThen with Matchers {

    val subject: CommandLineArgsParser = new CommandLineArgsParser

    Feature("Parsing command line input") {
        Scenario("Shows help message") {
            Given("basic usage")
            val input = Array.empty[String];


            When("No input")
            the[TaxiTripsReportException] thrownBy (subject.parse(input: _*))
        }

        Scenario("Help flag is selected") {
            Given("basic usage")
            val input = Array("-h")
            val input2 = Array("--help")

            val cliArgs = new CommandLineArguments

            When("No input")
            noException should be thrownBy (subject.parse(input: _*))
            noException should be thrownBy (subject.parse(input2: _*))
        }

        Scenario("Should select all the short parameters") {
            Given("basic usage")
            val input = Array(
                "-d", "168",
                "-e", "2024-09-16T19:56:35",
                "-fg", "someFile1,someFile2",
                "-fy", "someFile3,someFile4",
                "-g", "T",
                "-y", "F",
                "-p", "168",
                "-s", "2023-09-16T19:56:35",
                "-v", "1"
            )

            And("Expected")
            val expected: CommandLineArguments = new CommandLineArguments
            val filesGreen: List[String] = "someFile1,someFile2".split(",").toList
            val filesYellow: List[String] = "someFile3,someFile4".split(",").toList
            expected.setGreenFilesPaths(filesGreen.asJava)
            expected.setYellowFilesPaths(filesYellow.asJava)
            expected.setStartTime(LocalDateTime.parse("2023-09-16T19:56:35", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            expected.setEndDateTime(LocalDateTime.parse("2024-09-16T19:56:35", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            expected.setGreenTaxi("T")
            expected.setYellowTaxi("F")
            expected.setVendorId(1)
            expected.setPickUpLocationId(168)
            expected.setDropOffLocationId(168)
            expected.setHelp(false)

            When("Parsed")
            val cliArgs = subject.parse(input: _*)
            Then(" values match expectation")
            expected shouldBe cliArgs

        }

        Scenario("Should select all the long parameters") {
            Given("basic usage")
            val input = Array(
                "--dropOff", "169",
                "--end", "2024-09-16T19:56:35",
                "--filesGreen", "someFile1,someFile2",
                "--filesYellow", "someFile3,someFile4",
                "--isGreen", "T",
                "--isYellow", "F",
                "--pickUp", "168",
                "--start", "2023-09-16T19:56:35",
                "--vendor", "1"
            )

            And("Expected")
            val expected: CommandLineArguments = new CommandLineArguments
            val filesGreen: List[String] = "someFile1,someFile2".split(",").toList
            val fileYellows: List[String] = "someFile3,someFile4".split(",").toList
            expected.setGreenFilesPaths(filesGreen.asJava)
            expected.setYellowFilesPaths(fileYellows.asJava)
            expected.setStartTime(LocalDateTime.parse("2023-09-16T19:56:35", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            expected.setEndDateTime(LocalDateTime.parse("2024-09-16T19:56:35", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            expected.setGreenTaxi("T")
            expected.setYellowTaxi("F")
            expected.setVendorId(1)
            expected.setPickUpLocationId(168)
            expected.setDropOffLocationId(169)
            expected.setHelp(false)

            When("Parsed")
            val cliArgs = subject.parse(input: _*)
            Then(" values match expectation")
            expected shouldBe cliArgs

        }

    }

}
