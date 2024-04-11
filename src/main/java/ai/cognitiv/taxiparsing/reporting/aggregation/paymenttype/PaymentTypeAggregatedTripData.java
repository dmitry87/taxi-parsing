package ai.cognitiv.taxiparsing.reporting.aggregation.paymenttype;

import ai.cognitiv.taxiparsing.reporting.aggregation.AbstractBaseAggregatedTripData;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentTypeAggregatedTripData extends AbstractBaseAggregatedTripData {

  //We would like to be able to find aggregate information (min fare, max fare, count,
////sum of fares, and sum of toll fares, counts by payment type)
  private Double paymentType;

  public static PaymentTypeAggregatedTripData reduce(PaymentTypeAggregatedTripData left, PaymentTypeAggregatedTripData right) {
    if (right == null) {
      return left;
    }
    if (left == null) {
      left = new PaymentTypeAggregatedTripData();
    }
    left.setPaymentType(Optional.ofNullable(left.paymentType)
        .orElse(right.getPaymentType()));

    double minFareNullSafe = left.getMinFare() != null && right.getMinFare() != null ? Math.min(left.getMinFare(), right.getMinFare()) :
        Optional.ofNullable(left.getMinFare())
            .orElse(right.getMinFare());
    double maxFareNullSafe = left.getMaxFare() != null && right.getMaxFare() != null ? Math.max(left.getMaxFare(), right.getMaxFare()) :
        Optional.ofNullable(left.getMaxFare())
            .orElse(right.getMaxFare());

    double fareSumNullSafe = left.getFareSum() != null && right.getFareSum() != null ? left.getFareSum() + right.getFareSum() :
        Optional.ofNullable(left.getFareSum())
            .orElse(right.getFareSum());
    double tollsSumNullSafe = left.getTollsSum() != null && right.getTollsSum() != null ? left.getTollsSum() + right.getTollsSum() :
        Optional.ofNullable(left.getTollsSum())
            .orElse(right.getTollsSum());

    Long countNullSafe = Optional.ofNullable(left.getCount())
        .map(v -> v + 1)
        .orElse(1L);


    left.setMinFare(Math.round(minFareNullSafe * 100) / 100.);
    left.setMaxFare(Math.round(maxFareNullSafe * 100) / 100.);
    left.setCount(countNullSafe);
    left.setFareSum(Math.round(fareSumNullSafe * 100) / 100.);
    left.setTollsSum(Math.round(tollsSumNullSafe * 100) / 100.);

    return left;
  }

}
