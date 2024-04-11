package ai.cognitiv.taxiparsing.comandline;

import com.beust.jcommander.IStringConverter;
import java.util.Locale;
import java.util.Objects;

public class BooleanConverter implements IStringConverter<Boolean> {

  @Override
  public Boolean convert(String value) {
    return Objects.equals("T", value.toLowerCase(Locale.ROOT));
  }
}
