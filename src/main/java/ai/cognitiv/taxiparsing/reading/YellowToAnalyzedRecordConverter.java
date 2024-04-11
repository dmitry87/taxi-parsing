package ai.cognitiv.taxiparsing.reading;

import ai.cognitiv.taxiparsing.reading.conversion.TaxiColor;
import ai.cognitiv.taxiparsing.reading.conversion.YellowTripRecord;

public class YellowToAnalyzedRecordConverter implements Converter<YellowTripRecord, AnalyzedTripRecord> {

  @Override
  public AnalyzedTripRecord convert(YellowTripRecord value) {
    return AnalyzedTripRecord.of(
        value.getVendorID(),
        value.getTpepPickUpDatetime(),
        value.getTpepDropOffDatetime(),
        value.getFareAmount(),
        value.getTollsAmount(),
        value.getPaymentType() == null ? null : value.getPaymentType().doubleValue(),
        value.getPickUpLocationId(),
        value.getDropOffLocationId(),
        TaxiColor.YELLOW

    );
  }
}
