package ai.cognitiv.taxiparsing;

import ai.cognitiv.taxiparsing.appcontext.CommandBuilder;
import ai.cognitiv.taxiparsing.appcontext.GreenPredicatesBuilder;
import ai.cognitiv.taxiparsing.appcontext.PathResolver;
import ai.cognitiv.taxiparsing.appcontext.TaxiReaderBuilder;
import ai.cognitiv.taxiparsing.appcontext.YellowPredicatesBuilder;
import ai.cognitiv.taxiparsing.comandline.ArgsParser;
import ai.cognitiv.taxiparsing.comandline.CommandLineArgsParser;
import ai.cognitiv.taxiparsing.comandline.CommandLineArguments;
import ai.cognitiv.taxiparsing.config.ConfigurationParams;
import ai.cognitiv.taxiparsing.config.DimensionsConfig;
import ai.cognitiv.taxiparsing.reading.GreenToAnalyzedRecordConverter;
import ai.cognitiv.taxiparsing.reading.YellowToAnalyzedRecordConverter;
import ai.cognitiv.taxiparsing.reading.conversion.ConvertersFactory;
import ai.cognitiv.taxiparsing.reporting.DataReporter;
import ai.cognitiv.taxiparsing.reporting.aggregation.ConcatinatingGroupReport;
import ai.cognitiv.taxiparsing.reporting.aggregation.GroupedReportProcessor;
import ai.cognitiv.taxiparsing.reporting.aggregation.paymenttype.PaymentTypeGroupingReport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaxiParsingApplication {

  public static void main(String[] args) {
    ArgsParser parser = new CommandLineArgsParser();
    CommandLineArguments config = parser.parse(args);
    if (config == null) {
      return;
    }

    DimensionsConfig dimensionConfig = new DimensionsConfig(config);
    ConfigurationParams confParams = ConfigurationParams.builder()
        .dimensionsConfig(dimensionConfig)
        .greenTaxisPaths(config.getGreenFilesPaths())
        .yellowTaxisPaths(config.getYellowFilesPaths())
        .build();
    GreenPredicatesBuilder greenPredicates = new GreenPredicatesBuilder();
    YellowPredicatesBuilder yellowPredicates = new YellowPredicatesBuilder();
    TaxiReaderBuilder taxiReaderBuilder = new TaxiReaderBuilder(new ConvertersFactory());
    GreenToAnalyzedRecordConverter greenToAnalyzedConverter = new GreenToAnalyzedRecordConverter();
    YellowToAnalyzedRecordConverter yellowToAnalyzedRecordConverter = new YellowToAnalyzedRecordConverter();
    ConcatinatingGroupReport concatenatingReport = new ConcatinatingGroupReport<>(new PaymentTypeGroupingReport());
    DataReporter dataReporter = new DataReporter(concatenatingReport, new GroupedReportProcessor());
    PathResolver pathResolver = new PathResolver();

    Command command = new CommandBuilder(greenPredicates, yellowPredicates, taxiReaderBuilder, greenToAnalyzedConverter,
        yellowToAnalyzedRecordConverter, dataReporter, pathResolver)
        .buildCommand(confParams);

    command.execute();

  }

}
