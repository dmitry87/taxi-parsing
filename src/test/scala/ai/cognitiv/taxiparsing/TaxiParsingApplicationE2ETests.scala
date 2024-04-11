package ai.cognitiv.taxiparsing

import ai.cognitiv.taxiparsing.appcontext._
import ai.cognitiv.taxiparsing.comandline.CommandLineArguments
import ai.cognitiv.taxiparsing.config.{ConfigurationParams, DimensionsConfig}
import ai.cognitiv.taxiparsing.reading.conversion.ConvertersFactory
import ai.cognitiv.taxiparsing.reading.{GreenToAnalyzedRecordConverter, YellowToAnalyzedRecordConverter}
import ai.cognitiv.taxiparsing.reporting.DataReporter
import ai.cognitiv.taxiparsing.reporting.aggregation.paymenttype.{PaymentTypeAggregatedTripData, PaymentTypeGroupingReport}
import ai.cognitiv.taxiparsing.reporting.aggregation.{ConcatinatingGroupReport, GroupedReportProcessor}

import scala.jdk.CollectionConverters._

class TaxiParsingApplicationE2ETests extends TestBase {

    Feature("Should generate report with no filtration") {
        Scenario("Read all Green taxis and aggregate") {
            Given("Config")
            val cliArgs: CommandLineArguments = new CommandLineArguments
            //            cliArgs.setStartTime(LocalDateTime.parse("2023-10-01T00:00:01"))
            cliArgs.setYellowTaxi("f")
            val greenPath = this.getClass.getResource("/samples/generictaxireader10/green_tripdata_sample.parquet/part-00000-c674d580-103a-4ab2-90af-d4026e6d5b03-c000.snappy.parquet").getPath
            val yellowPath = this.getClass.getResource("/samples/generictaxireader10/yellow_tripdata_sample.parquet/part-00000-f8398954-690f-4965-9418-65d653b55f3e-c000.snappy.parquet").getPath
            val gPaths = List(greenPath)
            val yPaths = List(yellowPath)

            val dimensionConfig = new DimensionsConfig(cliArgs)
            val confParams = ConfigurationParams.builder()
                .dimensionsConfig(dimensionConfig)
                .greenTaxisPaths(gPaths.asJava)
                .yellowTaxisPaths(yPaths.asJava)
                .build()
            val greenPredicates = new GreenPredicatesBuilder
            val yellowPredicates = new YellowPredicatesBuilder
            val taxiReaderBuilder = new TaxiReaderBuilder(new ConvertersFactory)
            val greenToAnalyzedConverter = new GreenToAnalyzedRecordConverter
            val yellowToAnalyzedRecordConverter = new YellowToAnalyzedRecordConverter
            val concatenatingReport = new ConcatinatingGroupReport(new PaymentTypeGroupingReport)
            val dataReporter = new DataReporter(concatenatingReport, new GroupedReportProcessor())
            val pathResolver = new PathResolver

            val commandBuilder = new CommandBuilder(greenPredicates, yellowPredicates, taxiReaderBuilder, greenToAnalyzedConverter,
                yellowToAnalyzedRecordConverter, dataReporter, pathResolver)
                .buildCommand(confParams)

            And("Expected green map")
            val greenMap = Map(
                1D -> PaymentTypeAggregatedTripData.builder().paymentType(1).minFare(9.3).maxFare(13.5).count(5L).fareSum(59.8).tollsSum(0D).build(),
                2D -> PaymentTypeAggregatedTripData.builder().paymentType(2).minFare(6.5).maxFare(35.9).count(5L).fareSum(83.6).tollsSum(0D).build()
            )

            val result = commandBuilder.execute().asScala
            result should contain theSameElementsAs (greenMap)
        }

        Scenario("Read all Yellow taxis and aggregate") {
            Given("Config")
            val cliArgs: CommandLineArguments = new CommandLineArguments
            cliArgs.setGreenTaxi("f")
            val greenPath = this.getClass.getResource("/samples/generictaxireader10/green_tripdata_sample.parquet/part-00000-c674d580-103a-4ab2-90af-d4026e6d5b03-c000.snappy.parquet").getPath
            val yellowPath = this.getClass.getResource("/samples/generictaxireader10/yellow_tripdata_sample.parquet/part-00000-f8398954-690f-4965-9418-65d653b55f3e-c000.snappy.parquet").getPath
            val gPaths = List(greenPath)
            val yPaths = List(yellowPath)

            val dimensionConfig = new DimensionsConfig(cliArgs)
            val confParams = ConfigurationParams.builder()
                .dimensionsConfig(dimensionConfig)
                .greenTaxisPaths(gPaths.asJava)
                .yellowTaxisPaths(yPaths.asJava)
                .build()
            val greenPredicates = new GreenPredicatesBuilder
            val yellowPredicates = new YellowPredicatesBuilder
            val taxiReaderBuilder = new TaxiReaderBuilder(new ConvertersFactory)
            val greenToAnalyzedConverter = new GreenToAnalyzedRecordConverter
            val yellowToAnalyzedRecordConverter = new YellowToAnalyzedRecordConverter
            val concatenatingReport = new ConcatinatingGroupReport(new PaymentTypeGroupingReport)
            val dataReporter = new DataReporter(concatenatingReport, new GroupedReportProcessor)
            val pathResolver = new PathResolver

            val commandBuilder = new CommandBuilder(greenPredicates, yellowPredicates, taxiReaderBuilder, greenToAnalyzedConverter,
                yellowToAnalyzedRecordConverter, dataReporter, pathResolver)
                .buildCommand(confParams)

            And("Expected yellow map")
            val yellowMap = Map(
                1D -> PaymentTypeAggregatedTripData.builder().paymentType(1).minFare(5.8).maxFare(37.3).count(7L).fareSum(105.7).tollsSum(6.94).build(),
                2D -> PaymentTypeAggregatedTripData.builder().paymentType(2).minFare(3).maxFare(3).count(2L).fareSum(6).tollsSum(0D).build(),
                3D -> PaymentTypeAggregatedTripData.builder().paymentType(3).minFare(3).maxFare(3).count(1L).fareSum(3).tollsSum(0D).build()
            )
            When("Command is run")
            val result = commandBuilder.execute().asScala
            Then("Data is matching")
            result should contain theSameElementsAs (yellowMap)
        }

        Scenario("Read all Green and Yellow and aggregate") {
            Given("Config")
            val cliArgs: CommandLineArguments = new CommandLineArguments
            val greenPath = this.getClass.getResource("/samples/generictaxireader10/green_tripdata_sample.parquet/part-00000-c674d580-103a-4ab2-90af-d4026e6d5b03-c000.snappy.parquet").getPath
            val yellowPath = this.getClass.getResource("/samples/generictaxireader10/yellow_tripdata_sample.parquet/part-00000-f8398954-690f-4965-9418-65d653b55f3e-c000.snappy.parquet").getPath
            val gPaths = List(greenPath)
            val yPaths = List(yellowPath)

            val dimensionConfig = new DimensionsConfig(cliArgs)
            val confParams = ConfigurationParams.builder()
                .dimensionsConfig(dimensionConfig)
                .greenTaxisPaths(gPaths.asJava)
                .yellowTaxisPaths(yPaths.asJava)
                .build()
            val greenPredicates = new GreenPredicatesBuilder
            val yellowPredicates = new YellowPredicatesBuilder
            val taxiReaderBuilder = new TaxiReaderBuilder(new ConvertersFactory)
            val greenToAnalyzedConverter = new GreenToAnalyzedRecordConverter
            val yellowToAnalyzedRecordConverter = new YellowToAnalyzedRecordConverter
            val concatenatingReport = new ConcatinatingGroupReport(new PaymentTypeGroupingReport)
            val dataReporter = new DataReporter(concatenatingReport, new GroupedReportProcessor)
            val pathResolver = new PathResolver

            val commandBuilder = new CommandBuilder(greenPredicates, yellowPredicates, taxiReaderBuilder, greenToAnalyzedConverter,
                yellowToAnalyzedRecordConverter, dataReporter, pathResolver)
                .buildCommand(confParams)

            And("Expected map")
            val expected = Map(
                1D -> PaymentTypeAggregatedTripData.builder().paymentType(1).minFare(5.8).maxFare(37.3).count(12L).fareSum(165.5).tollsSum(6.94).build(),
                2D -> PaymentTypeAggregatedTripData.builder().paymentType(2).minFare(3).maxFare(35.9).count(7L).fareSum(89.6).tollsSum(0D).build(),
                3D -> PaymentTypeAggregatedTripData.builder().paymentType(3).minFare(3).maxFare(3).count(1L).fareSum(3).tollsSum(0D).build()
            )

            When("Command is run")
            val result = commandBuilder.execute().asScala
            Then("Should match expectation")
            result should contain theSameElementsAs (expected)
        }
    }

}
