package ai.cognitiv.taxiparsing.reporting.aggregation.color;

import ai.cognitiv.taxiparsing.reading.conversion.TaxiColor;
import ai.cognitiv.taxiparsing.reporting.aggregation.AbstractBaseAggregatedTripData;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColorTypeAggregatedTripData extends AbstractBaseAggregatedTripData {

  private TaxiColor taxiColor;

  public static ColorTypeAggregatedTripData reduce(ColorTypeAggregatedTripData left, ColorTypeAggregatedTripData right) {
    if (right == null) {
      return left;
    }
    if (left == null) {
      left = new ColorTypeAggregatedTripData();
    }
    left.setTaxiColor(Optional.ofNullable(left.getTaxiColor())
        .orElse(right.getTaxiColor()));

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
