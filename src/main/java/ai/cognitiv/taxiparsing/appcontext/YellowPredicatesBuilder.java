
package ai.cognitiv.taxiparsing.appcontext;

import ai.cognitiv.taxiparsing.config.DimensionsConfig;
import ai.cognitiv.taxiparsing.reading.conversion.YellowTripRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class YellowPredicatesBuilder {

  public List<Predicate<YellowTripRecord>> buildPredicatesList(DimensionsConfig input) {
    List<Predicate<YellowTripRecord>> predicates = new ArrayList<>();
    if (input.getStart() != null) {
      predicates.add(greenTrip -> greenTrip.getTpepPickUpDatetime().isAfter(input.getStart()));
    }
    if (!input.isYellowTaxi()) {
      predicates.add(greenTrip -> false); // drop green filter
    }
    if (input.getEnd() != null) {
      predicates.add(greenTrip -> greenTrip.getTpepPickUpDatetime().isBefore(input.getEnd()));
    }
    if (input.getVendorId() != null) {
      predicates.add(greenTrip -> input.getVendorId().equals(greenTrip.getVendorID()));
    }
    if (input.getPickUpLocationId() != null) {
      predicates.add(greenTrip -> input.getPickUpLocationId().equals(greenTrip.getPickUpLocationId()));
    }
    if (input.getDropOffLocationId() != null) {
      predicates.add(greenTrip -> input.getDropOffLocationId().equals(greenTrip.getDropOffLocationId()));
    }

    return predicates;

  }

}
