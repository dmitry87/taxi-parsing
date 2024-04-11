package ai.cognitiv.taxiparsing.reading.conversion;

public enum TaxiColor {

  YELLOW("yellow"), GREEN("green");


  TaxiColor(String color) {
    this.color = color;
  }

  private final String color;
}
