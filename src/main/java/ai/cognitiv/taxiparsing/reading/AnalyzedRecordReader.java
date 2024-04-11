package ai.cognitiv.taxiparsing.reading;

import java.util.stream.Stream;

public class AnalyzedRecordReader<S> implements TaxiReader<AnalyzedTripRecord> {

  private final TaxiReader<S> sourceFileReader;
  private final Converter<S, AnalyzedTripRecord> targetConverter;

  public AnalyzedRecordReader(TaxiReader<S> sourceFileReader, Converter<S, AnalyzedTripRecord> targetConverter) {
    this.sourceFileReader = sourceFileReader;
    this.targetConverter = targetConverter;
  }

  @Override
  public Stream<AnalyzedTripRecord> readInput() {
    return sourceFileReader.readInput()
        .map(targetConverter::convert);
  }

  @Override
  public Class<AnalyzedTripRecord> getType() {
    return AnalyzedTripRecord.class;
  }
}
