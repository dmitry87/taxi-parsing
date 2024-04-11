package ai.cognitiv.taxiparsing.appcontext;

import ai.cognitiv.taxiparsing.Command;
import ai.cognitiv.taxiparsing.config.ConfigurationParams;
import ai.cognitiv.taxiparsing.reading.AnalyzedRecordReader;
import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reading.Converter;
import ai.cognitiv.taxiparsing.reading.conversion.GreenTripRecord;
import ai.cognitiv.taxiparsing.reading.conversion.YellowTripRecord;
import ai.cognitiv.taxiparsing.reporting.DataReporter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CommandBuilder {

  private final GreenPredicatesBuilder greenPredicatesBuilder;
  private final YellowPredicatesBuilder yellowPredicatesBuilder;
  private final TaxiReaderBuilder taxiReaderBuilder;
  private final Converter<GreenTripRecord, AnalyzedTripRecord> greenToAnalyzedConverter;
  private final Converter<YellowTripRecord, AnalyzedTripRecord> yellowToAnalyzedConverter;
  private final DataReporter dataReporter;
  private final PathResolver pathResolver;

  public CommandBuilder(GreenPredicatesBuilder greenPredicatesBuilder, YellowPredicatesBuilder yellowPredicatesBuilder,
                        TaxiReaderBuilder taxiReaderBuilder, Converter<GreenTripRecord, AnalyzedTripRecord> greenToAnalyzedConverter,
                        Converter<YellowTripRecord, AnalyzedTripRecord> yellowToAnalyzedConverter,
                        DataReporter dataReporter, PathResolver pathResolver) {
    this.greenPredicatesBuilder = greenPredicatesBuilder;
    this.yellowPredicatesBuilder = yellowPredicatesBuilder;
    this.taxiReaderBuilder = taxiReaderBuilder;
    this.greenToAnalyzedConverter = greenToAnalyzedConverter;
    this.yellowToAnalyzedConverter = yellowToAnalyzedConverter;
    this.dataReporter = dataReporter;
    this.pathResolver = pathResolver;
  }

  public Command buildCommand(ConfigurationParams config) {
    List<String> greenPaths = pathResolver.resolvePaths(config.getDimensionsConfig().isGreenTaxi(), config.getGreenTaxisPaths(),
        new String[0]);
    List<String> yellowPaths = pathResolver.resolvePaths(config.getDimensionsConfig().isYellowTaxi(), config.getYellowTaxisPaths(),
        new String[0]);

    List<Predicate<YellowTripRecord>> yellowPredicates = yellowPredicatesBuilder.buildPredicatesList(config.getDimensionsConfig());
    List<Predicate<GreenTripRecord>> greenPredicates = greenPredicatesBuilder.buildPredicatesList(config.getDimensionsConfig());

    Stream<AnalyzedRecordReader<AnalyzedTripRecord>> greenStream = greenPaths.stream()
        .map(path -> taxiReaderBuilder.buildGenericReader(path, GreenTripRecord.class))
        .map(genericReader -> taxiReaderBuilder.buildFiltratingReader(greenPredicates, genericReader))
        .map(filteredReader -> taxiReaderBuilder.buildAnalyzedRecordBuilder(filteredReader, greenToAnalyzedConverter));

    Stream<AnalyzedRecordReader<AnalyzedTripRecord>> yellowStream = yellowPaths.stream()
        .map(path -> taxiReaderBuilder.buildGenericReader(path, YellowTripRecord.class))
        .map(genericReader -> taxiReaderBuilder.buildFiltratingReader(yellowPredicates, genericReader))
        .map(filteredReader -> taxiReaderBuilder.buildAnalyzedRecordBuilder(filteredReader, yellowToAnalyzedConverter));

    return new Command(greenStream, yellowStream, dataReporter);

  }

}
