package ai.cognitiv.taxiparsing;

import ai.cognitiv.taxiparsing.reading.AnalyzedRecordReader;
import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reporting.DataReporter;
import ai.cognitiv.taxiparsing.reporting.aggregation.AbstractBaseAggregatedTripData;
import java.util.Map;
import java.util.stream.Stream;

public class Command {

  private final Stream<AnalyzedRecordReader<AnalyzedTripRecord>> greenStream;
  private final Stream<AnalyzedRecordReader<AnalyzedTripRecord>> yellowStream;
  private final DataReporter dataReporter;

  public Command(Stream<AnalyzedRecordReader<AnalyzedTripRecord>> greenStream, Stream<AnalyzedRecordReader<AnalyzedTripRecord>> yellowStream, DataReporter dataReporter) {

      this.greenStream = greenStream;
      this.yellowStream = yellowStream;
      this.dataReporter = dataReporter;
  }

  public Map<?, ? extends AbstractBaseAggregatedTripData> execute() {
    return dataReporter.reportData(greenStream, yellowStream);



  }



}
