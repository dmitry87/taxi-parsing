package ai.cognitiv.taxiparsing.reporting.aggregation.color;

import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reading.TaxiReader;
import ai.cognitiv.taxiparsing.reading.conversion.TaxiColor;
import ai.cognitiv.taxiparsing.reporting.aggregation.GroupedReportProvider;
import ai.cognitiv.taxiparsing.reporting.aggregation.color.ColorTypeAggregatedTripData;
import java.util.Map;
import java.util.stream.Collectors;

public class ColorGroupingReport implements GroupedReportProvider<TaxiColor, ColorTypeAggregatedTripData> {



  public ColorGroupingReport() {
  }

  public Map<TaxiColor, ColorTypeAggregatedTripData> prepareGroupedReport(TaxiReader<AnalyzedTripRecord> groupedDataReader) {

    return groupedDataReader.readInput()
        .collect(Collectors.toMap(AnalyzedTripRecord::getColor, this::convert, ColorTypeAggregatedTripData::reduce));

  }

  private ColorTypeAggregatedTripData convert(AnalyzedTripRecord analyzed) {
    return ColorTypeAggregatedTripData.builder()
        .minFare(analyzed.getFareAmount())
        .maxFare(analyzed.getFareAmount())
        .count(1L)
        .fareSum(analyzed.getFareAmount())
        .tollsSum(analyzed.getTollAmount())
        .taxiColor(analyzed.getColor())
        .build();
  }
}
