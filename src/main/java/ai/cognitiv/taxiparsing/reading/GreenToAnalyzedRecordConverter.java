package ai.cognitiv.taxiparsing.reading;

import ai.cognitiv.taxiparsing.reading.conversion.GreenTripRecord;
import ai.cognitiv.taxiparsing.reading.conversion.TaxiColor;

public class GreenToAnalyzedRecordConverter implements Converter<GreenTripRecord, AnalyzedTripRecord> {

  @Override
  public AnalyzedTripRecord convert(GreenTripRecord value) {
    return AnalyzedTripRecord.of(
        value.getVendorId(),
        value.getLpepPickUpDatetime(),
        value.getLpepDropOffDatetime(),
        value.getFareAmount(),
        value.getTollsAmount(),
        value.getPaymentType(),
        value.getPickUpLocationId(),
        value.getDropOffLocationId(),
        TaxiColor.GREEN
    );
  }
}
