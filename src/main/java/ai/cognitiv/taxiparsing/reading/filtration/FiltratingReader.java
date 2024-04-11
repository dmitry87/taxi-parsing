package ai.cognitiv.taxiparsing.reading.filtration;

import ai.cognitiv.taxiparsing.reading.TaxiReader;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FiltratingReader<T> implements TaxiReader<T> {

  private final List<Predicate<T>> filterParameters;
  private final TaxiReader<T> recordTaxiReader;

  public FiltratingReader(List<Predicate<T>> filterParameters, TaxiReader<T> recordTaxiReader) {
    this.filterParameters = filterParameters;
    this.recordTaxiReader = recordTaxiReader;
  }


  @Override
  public Stream<T> readInput() {
    Predicate<T> sumPredicate = filterParameters.stream()
        .reduce(first -> true, Predicate::and);
    return recordTaxiReader.readInput()
        .filter(sumPredicate);
  }

  @Override
  public Class<T> getType() {
    return recordTaxiReader.getType();
  }
}