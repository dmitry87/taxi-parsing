package ai.cognitiv.taxiparsing.reading.conversion;

import ai.cognitiv.taxiparsing.reading.Converter;
import java.time.LocalDateTime;
import java.util.Map;

public class YellowTripConverter implements Converter<Map<String, Object>, YellowTripRecord> {

  @Override
  public YellowTripRecord convert(Map<String, Object> value) {
    Long vendorId = value.get(YellowTripRecord.ColumnNames.VENDOR_ID) == null ? null :
            (Long) value.get(YellowTripRecord.ColumnNames.VENDOR_ID);
    return new YellowTripRecord(
        vendorId,
        (LocalDateTime) value.get(YellowTripRecord.ColumnNames.TPEP_PICKUP_DATETIME),
        (LocalDateTime) value.get(YellowTripRecord.ColumnNames.TPEP_DROPOFF_DATETIME),
        (Double) value.get(YellowTripRecord.ColumnNames.PASSENGER_COUNT),
        (Double) value.get(YellowTripRecord.ColumnNames.TRIP_DISTANCE),
        (Double) value.get(YellowTripRecord.ColumnNames.RATECODEID),
        (String) value.get(YellowTripRecord.ColumnNames.STORE_AND_FWD_FLAG),
        (Long) value.get(YellowTripRecord.ColumnNames.PULOCATIONID),
        (Long) value.get(YellowTripRecord.ColumnNames.DOLOCATIONID),
        (Long) value.get(YellowTripRecord.ColumnNames.PAYMENT_TYPE),
        (Double) value.get(YellowTripRecord.ColumnNames.FARE_AMOUNT),
        (Double) value.get(YellowTripRecord.ColumnNames.EXTRA),
        (Double) value.get(YellowTripRecord.ColumnNames.MTA_TAX),
        (Double) value.get(YellowTripRecord.ColumnNames.TIP_AMOUNT),
        (Double) value.get(YellowTripRecord.ColumnNames.TOLLS_AMOUNT),
        (Double) value.get(YellowTripRecord.ColumnNames.IMPROVEMENT_SURCHARGE),
        (Double) value.get(YellowTripRecord.ColumnNames.TOTAL_AMOUNT),
        (Double) value.get(YellowTripRecord.ColumnNames.CONGESTION_SURCHARGE),
        (Double) value.get(YellowTripRecord.ColumnNames.AIRPORT_FEE)
    );
  }
}
