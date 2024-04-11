package ai.cognitiv.taxiparsing.reporting.aggregation;

import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reading.TaxiReader;
import java.util.Map;

public interface GroupedReportProvider<G, A> {

  Map<G, A> prepareGroupedReport(TaxiReader<AnalyzedTripRecord> groupedDataReader);

}
