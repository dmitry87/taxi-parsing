package ai.cognitiv.taxiparsing.reporting.aggregation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public abstract class AbstractBaseAggregatedTripData {

  private Double minFare;
  private Double maxFare;
  private Long count;
  private Double fareSum;
  private Double tollsSum;
}
