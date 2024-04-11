package ai.cognitiv.taxiparsing.reading;

public interface Converter<S, T> {

  T convert(S value);
}
