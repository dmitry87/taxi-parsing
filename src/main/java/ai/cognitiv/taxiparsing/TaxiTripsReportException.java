package ai.cognitiv.taxiparsing;

import com.beust.jcommander.ParameterException;

public class TaxiTripsReportException extends RuntimeException {

  public TaxiTripsReportException(String message) {
    super(message);
  }

  public TaxiTripsReportException(String localizedMessage, ParameterException ex) {
    super(localizedMessage, ex);
  }
}
