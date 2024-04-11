package ai.cognitiv.taxiparsing.reporting;

import ai.cognitiv.taxiparsing.reading.AnalyzedRecordReader;
import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reporting.aggregation.AbstractBaseAggregatedTripData;
import ai.cognitiv.taxiparsing.reporting.aggregation.ConcatinatingGroupReport;
import ai.cognitiv.taxiparsing.reporting.aggregation.GroupedReportProcessor;
import java.util.Map;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataReporter {

  private final ConcatinatingGroupReport concatinatingGroupReport;
  private final GroupedReportProcessor reportPrinter;

  public DataReporter(ConcatinatingGroupReport concatinatingGroupReport, GroupedReportProcessor reportPrinter) {
    this.concatinatingGroupReport = concatinatingGroupReport;
    this.reportPrinter = reportPrinter;
  }


  public Map<?, ? extends AbstractBaseAggregatedTripData> reportData(Stream<AnalyzedRecordReader<AnalyzedTripRecord>> greenStream,
                                                                     Stream<AnalyzedRecordReader<AnalyzedTripRecord>> yellowStream) {

    Map<?, ? extends AbstractBaseAggregatedTripData> result = concatinatingGroupReport.readInput(greenStream, yellowStream);
    reportPrinter.processReport(result);

    return result;
  }
}
