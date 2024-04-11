package ai.cognitiv.taxiparsing.reading;

import java.util.stream.Stream;

public interface TaxiReader<T> {

  Stream<T> readInput();

  Class<T> getType();

}
