package ai.cognitiv.taxiparsing.comandline;

import com.beust.jcommander.IStringConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements IStringConverter<LocalDateTime> {


  @Override
  public LocalDateTime convert(String s) {
    return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}
