package ai.cognitiv.taxiparsing.appcontext;

import ai.cognitiv.taxiparsing.reading.AnalyzedRecordReader;
import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reading.Converter;
import ai.cognitiv.taxiparsing.reading.GenericTaxiReader;
import ai.cognitiv.taxiparsing.reading.TaxiReader;
import ai.cognitiv.taxiparsing.reading.conversion.ConvertersFactory;
import ai.cognitiv.taxiparsing.reading.filtration.FiltratingReader;
import java.util.List;
import java.util.function.Predicate;

public class TaxiReaderBuilder {

  private final ConvertersFactory convertersFactory;

  public TaxiReaderBuilder(ConvertersFactory convertersFactory) {
    this.convertersFactory = convertersFactory;
  }


  public <T> TaxiReader<T> buildGenericReader(String path, Class<T> clazz) {
    return new GenericTaxiReader<>(path, convertersFactory, clazz);
  }

  public <T> TaxiReader<T> buildFiltratingReader(List<Predicate<T>> predicates, TaxiReader<T> rawReader) {
    return new FiltratingReader<>(predicates, rawReader);
  }

  public <S> AnalyzedRecordReader<AnalyzedTripRecord> buildAnalyzedRecordBuilder(TaxiReader<S> sourceFileReader, Converter<S, AnalyzedTripRecord> targetConverter) {
    return  new AnalyzedRecordReader(sourceFileReader, targetConverter);
  }

}
