package ai.cognitiv.taxiparsing.reading.conversion;

import ai.cognitiv.taxiparsing.reading.Converter;
import java.time.LocalDateTime;
import java.util.Map;

public class GreenTripConverter implements Converter<Map<String, Object>, GreenTripRecord> {

  @Override
  public GreenTripRecord convert(Map<String, Object> value) {
    return new GreenTripRecord(
        (Long) value.get(GreenTripRecord.ColumnNames.VENDOR_ID),
        (LocalDateTime) value.get(GreenTripRecord.ColumnNames.LPEP_PICKUP_DATETIME),
        (LocalDateTime) value.get(GreenTripRecord.ColumnNames.LPEP_DROPOFF_DATETIME),
        (String) value.get(GreenTripRecord.ColumnNames.STORE_AND_FWD_FLAG),
        (Double) value.get(GreenTripRecord.ColumnNames.RATECODE_ID),
        (Long) value.get(GreenTripRecord.ColumnNames.PU_LOCATION_ID),
        (Long) value.get(GreenTripRecord.ColumnNames.DO_LOCATION_ID),
        (Double) value.get(GreenTripRecord.ColumnNames.PASSENGER_COUNT),
        (Double) value.get(GreenTripRecord.ColumnNames.TRIP_DISTANCE),
        (Double) value.get(GreenTripRecord.ColumnNames.FARE_AMOUNT),
        (Double) value.get(GreenTripRecord.ColumnNames.EXTRA),
        (Double) value.get(GreenTripRecord.ColumnNames.MTA_TAX),
        (Double) value.get(GreenTripRecord.ColumnNames.TIP_AMOUNT),
        (Double) value.get(GreenTripRecord.ColumnNames.TOLLS_AMOUNT),
        (Double) value.get(GreenTripRecord.ColumnNames.EHAIL_FEE),
        (Double) value.get(GreenTripRecord.ColumnNames.IMPROVEMENT_SURCHARGE),
        (Double) value.get(GreenTripRecord.ColumnNames.TOTAL_AMOUNT),
        (Double) value.get(GreenTripRecord.ColumnNames.PAYMENT_TYPE),
        (Double) value.get(GreenTripRecord.ColumnNames.TRIP_TYPE));
  }
}
