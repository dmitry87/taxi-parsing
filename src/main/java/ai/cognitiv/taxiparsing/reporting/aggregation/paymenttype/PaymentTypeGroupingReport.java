package ai.cognitiv.taxiparsing.reporting.aggregation.paymenttype;

import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reading.TaxiReader;
import ai.cognitiv.taxiparsing.reporting.aggregation.GroupedReportProvider;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaymentTypeGroupingReport implements GroupedReportProvider<Double, PaymentTypeAggregatedTripData> {

  public Map<Double, PaymentTypeAggregatedTripData> prepareGroupedReport(TaxiReader<AnalyzedTripRecord> groupedDataReader) {
    Stream<AnalyzedTripRecord> readInput = groupedDataReader.readInput();

    return readInput
        .collect(Collectors.toMap(AnalyzedTripRecord::getPaymentType, this::convert, PaymentTypeAggregatedTripData::reduce));
  }

  private PaymentTypeAggregatedTripData convert(AnalyzedTripRecord analyzed) {

    return PaymentTypeAggregatedTripData.builder()
        .minFare(analyzed.getFareAmount())
        .maxFare(analyzed.getFareAmount())
        .count(1L)
        .fareSum(analyzed.getFareAmount())
        .tollsSum(analyzed.getTollAmount())
        .paymentType(analyzed.getPaymentType())
        .build();
  }
}
