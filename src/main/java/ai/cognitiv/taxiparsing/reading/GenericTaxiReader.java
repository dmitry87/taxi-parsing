package ai.cognitiv.taxiparsing.reading;

import ai.cognitiv.taxiparsing.reading.conversion.ConvertersFactory;
import blue.strategic.parquet.Hydrator;
import blue.strategic.parquet.HydratorSupplier;
import blue.strategic.parquet.ParquetReader;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericTaxiReader<T> implements TaxiReader<T> {

  private final String path;
  private final ConvertersFactory convertersFactory;
  private final Class<T> curRecord;

  public GenericTaxiReader(String path, ConvertersFactory convertersFactory, Class<T> curRecord) {
    this.path = path;
    this.convertersFactory = convertersFactory;
    this.curRecord = curRecord;
  }

  @SneakyThrows
  @Override
  public Stream<T> readInput() {
    return ParquetReader.streamContent(new File(path), HydratorSupplier.constantly(hydrator))
        .map(v -> convertersFactory.getConverter(curRecord).convert(v));

  }

  @Override
  public Class<T> getType() {
    return curRecord;
  }

  private Hydrator<Map<String, Object>, Map<String, Object>> hydrator = new Hydrator<>() {
    @Override
    public Map<String, Object> start() {
      return new HashMap<>();
    }

    @Override
    public HashMap<String, Object> add(Map<String, Object> target, String heading, Object value) {
      HashMap<String, Object> r = new HashMap<>(target);
      try {
        log.debug("Converting {} column and value {}", heading, value);
        Object tmp = convertersFactory.getMapping(curRecord, heading).convert(value);
        r.put(heading.toLowerCase(Locale.ROOT), tmp);
        return r;
      } catch (Exception e) {
        log.error("Cannot parse type {} column: {}, with value: {}", curRecord, heading, value);
        throw e;
      }
    }

    @Override
    public Map<String, Object> finish(Map<String, Object> target) {
      return target;
    }
  };

}
