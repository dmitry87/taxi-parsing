package ai.cognitiv.taxiparsing.reporting.aggregation;

import ai.cognitiv.taxiparsing.reporting.aggregation.color.ColorTypeAggregatedTripData;
import ai.cognitiv.taxiparsing.reporting.aggregation.paymenttype.PaymentTypeAggregatedTripData;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

@Slf4j
public class GroupedReportProcessor {


  public <G, T extends AbstractBaseAggregatedTripData> void processReport(Map<G, T> aggregatedDataToPrint) {
    Optional<T> cur = aggregatedDataToPrint.values().stream().findFirst();
    if (cur.isPresent()) {
      T value = cur.get();
      if (value.getClass() == PaymentTypeAggregatedTripData.class) {
        Map<Double, PaymentTypeAggregatedTripData> paymentTypeMap = (Map<Double, PaymentTypeAggregatedTripData>) aggregatedDataToPrint;
        buildPaymentAggregatedTable(paymentTypeMap);
      } else if (value.getClass() == ColorTypeAggregatedTripData.class) {
        // TODO: Do we need color groups?
      }
    }

  }

  private void buildPaymentAggregatedTable(Map<Double, PaymentTypeAggregatedTripData> paymentTypeMap) {

    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(8);

    DoubleColumn paymentType = DoubleColumn.create("PaymentType");
    DoubleColumn minFare = DoubleColumn.create("MinFare");
    DoubleColumn maxFare = DoubleColumn.create("MaxFare");
    DoubleColumn count = DoubleColumn.create("Count");
    StringColumn fareSum = StringColumn.create("FareSum");
    StringColumn tollsSum = StringColumn.create("TollsSum");
    paymentTypeMap.entrySet().forEach(entry -> {
      paymentType.append(entry.getKey());
      minFare.append(entry.getValue().getMinFare());
      maxFare.append(entry.getValue().getMaxFare());
      count.append(entry.getValue().getCount());
      fareSum.append(df.format(entry.getValue().getFareSum()));
      tollsSum.append(df.format(entry.getValue().getTollsSum()));
    });


    Table resultTable = Table.create("Aggregated Data", paymentType, minFare, maxFare, count, fareSum, tollsSum);

    log.info(resultTable.print());

  }


}
