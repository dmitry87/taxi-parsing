package ai.cognitiv.taxiparsing.appcontext;

import ai.cognitiv.taxiparsing.config.DimensionsConfig;
import ai.cognitiv.taxiparsing.reading.conversion.GreenTripRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GreenPredicatesBuilder {

  public List<Predicate<GreenTripRecord>> buildPredicatesList(DimensionsConfig input) {
    List<Predicate<GreenTripRecord>> predicates = new ArrayList<>();
    if (input.getStart() != null) {
      predicates.add(greenTrip -> greenTrip.getLpepPickUpDatetime().isAfter(input.getStart()));
    }
    if (!input.isGreenTaxi()) {
      predicates.add(greenTrip -> false); // drop green filter
    }
    if (input.getEnd() != null) {
      predicates.add(greenTrip -> greenTrip.getLpepPickUpDatetime().isBefore(input.getEnd()));
    }
    if (input.getVendorId() != null) {
      predicates.add(greenTrip -> input.getVendorId().equals(greenTrip.getVendorId()));
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
