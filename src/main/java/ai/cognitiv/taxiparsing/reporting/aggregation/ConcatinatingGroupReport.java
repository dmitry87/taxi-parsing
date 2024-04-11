package ai.cognitiv.taxiparsing.reporting.aggregation;

import ai.cognitiv.taxiparsing.reading.AnalyzedRecordReader;
import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord;
import ai.cognitiv.taxiparsing.reporting.aggregation.color.ColorTypeAggregatedTripData;
import ai.cognitiv.taxiparsing.reporting.aggregation.paymenttype.PaymentTypeAggregatedTripData;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcatinatingGroupReport<G, AGG extends AbstractBaseAggregatedTripData> {

  private final GroupedReportProvider<G, AGG> groupedReportProvider;

  public ConcatinatingGroupReport(GroupedReportProvider<G, AGG> groupedReportProvider) {
    this.groupedReportProvider = groupedReportProvider;
  }


  public Map<G, AGG> readInput(Stream<AnalyzedRecordReader<AnalyzedTripRecord>> yellow,
                               Stream<AnalyzedRecordReader<AnalyzedTripRecord>> green) {
    Stream<Map<G, AGG>> yellowStream = yellow
        .map(groupedDataReader -> groupedReportProvider.prepareGroupedReport(groupedDataReader));
    log.info("Yellow reports processed");
    Stream<Map<G, AGG>> greenStream = green
        .map(groupedReportProvider::prepareGroupedReport);
    log.info("Green reports processed");
    Map<G, AGG> unordered = Stream.concat(yellowStream, greenStream)
        .flatMap(map -> map.entrySet().stream())
        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(), (l, r) -> (AGG) reduce(l, r)));

    log.info("Reports grouped into {}", unordered);

    return new TreeMap<>(unordered);
  }


  private AbstractBaseAggregatedTripData reduce(AbstractBaseAggregatedTripData left, AbstractBaseAggregatedTripData right) {
    AbstractBaseAggregatedTripData result = null;
    if (left.getClass() == ColorTypeAggregatedTripData.class) {
      ColorTypeAggregatedTripData newLeft = (ColorTypeAggregatedTripData) left;
      ColorTypeAggregatedTripData newRight = (ColorTypeAggregatedTripData) right;
      result = ColorTypeAggregatedTripData.reduce(newLeft, newRight);
    } else if (left.getClass() == PaymentTypeAggregatedTripData.class) {
      PaymentTypeAggregatedTripData newLeft = (PaymentTypeAggregatedTripData) left;
      PaymentTypeAggregatedTripData newRight = (PaymentTypeAggregatedTripData) right;
      result = PaymentTypeAggregatedTripData.reduce(newLeft, newRight);
    } else {
      log.error("Report type {} not supported!", left.getClass());
      throw new UnsupportedOperationException("This report type for class " + left.getClass() + " not supported!");
    }
    result.setMaxFare(Math.round(result.getMaxFare() * 100) / 100.0);
    result.setFareSum(Math.round(result.getFareSum() * 100) / 100.0);
    result.setMinFare(Math.round(result.getMinFare() * 100) / 100.0);
    result.setTollsSum((Math.round(result.getTollsSum() * 100) / 100.0));

    result.setCount(result.getCount() - 1 + right.getCount());
    return result;
  }

}
