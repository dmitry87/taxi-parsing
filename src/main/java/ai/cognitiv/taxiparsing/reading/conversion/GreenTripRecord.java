package ai.cognitiv.taxiparsing.reading.conversion;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class GreenTripRecord {

  Long vendorId;
  LocalDateTime lpepPickUpDatetime;
  LocalDateTime lpepDropOffDatetime;
  String storeAndFwdFlag;
  Double ratecodeID;
  Long pickUpLocationId;
  Long dropOffLocationId;
  Double passengerCount;
  Double tripDistance;
  Double fareAmount;
  Double extra;
  Double mtaTax;
  Double tipAmount;
  Double tollsAmount;
  Double ehailFee;
  Double improvementSurcharge;
  Double totalAmount;
  Double paymentType;
  Double tripType;

  static class ColumnNames {

    static final String VENDOR_ID = "vendorid";
    static final String LPEP_PICKUP_DATETIME = "lpep_pickup_datetime";
    static final String LPEP_DROPOFF_DATETIME = "lpep_dropoff_datetime";
    static final String STORE_AND_FWD_FLAG = "store_and_fwd_flag";
    static final String RATECODE_ID = "ratecodeid";
    static final String PU_LOCATION_ID = "pulocationid";
    static final String DO_LOCATION_ID = "dolocationid";
    static final String PASSENGER_COUNT = "passenger_count";
    static final String TRIP_DISTANCE = "trip_distance";
    static final String FARE_AMOUNT = "fare_amount";
    static final String EXTRA = "extra";
    static final String MTA_TAX = "mta_tax";
    static final String TIP_AMOUNT = "tip_amount";
    static final String TOLLS_AMOUNT = "tolls_amount";
    static final String EHAIL_FEE = "ehail_fee";
    static final String IMPROVEMENT_SURCHARGE = "improvement_surcharge";
    static final String TOTAL_AMOUNT = "total_amount";
    static final String PAYMENT_TYPE = "payment_type";
    static final String TRIP_TYPE = "trip_type";
    static final String CONGESTION_SURCHARGE = "congestion_surcharge";
  }

  private static class EntityNames {

    private EntityNames() {
    }

    public static final String VENDOR_ID = "vendorId";
    public static final String LPEP_PICKUP_DATETIME = "lpepPickUpDatetime";
    public static final String LPEP_DROPOFF_DATETIME = "lpepDropOffDatetime";
    public static final String STORE_AND_FWD_FLAG = "storeAndFwdFlag";
    public static final String RATECODE_ID = "ratecodeID";
    public static final String PU_LOCATION_ID = "pickUpLocationId";
    public static final String DO_LOCATION_ID = "dropOffLocationId";
    public static final String PASSENGER_COUNT = "passengerCount";
    public static final String TRIP_DISTANCE = "tripDistance";
    public static final String FARE_AMOUNT = "fareAmount";
    public static final String EXTRA = "extra";
    public static final String MTA_TAX = "mtaTax";
    public static final String TIP_AMOUNT = "tipAmount";
    public static final String TOLLS_AMOUNT = "tollsAmount";
    public static final String EHAIL_FEE = "ehailFee";
    public static final String IMPROVEMENT_SURCHARGE = "improvementSurcharge";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String TRIP_TYPE = "tripType";
    public static final String CONGESTION_SURCHARGE = "congestion_surcharge";

  }


  private static final Map<String, String> MAPPING = Map.ofEntries(
      Map.entry(ColumnNames.VENDOR_ID, EntityNames.VENDOR_ID),
      Map.entry(ColumnNames.LPEP_PICKUP_DATETIME, EntityNames.LPEP_PICKUP_DATETIME),
      Map.entry(ColumnNames.LPEP_DROPOFF_DATETIME, EntityNames.LPEP_DROPOFF_DATETIME),
      Map.entry(ColumnNames.STORE_AND_FWD_FLAG, EntityNames.STORE_AND_FWD_FLAG),
      Map.entry(ColumnNames.RATECODE_ID, EntityNames.RATECODE_ID),
      Map.entry(ColumnNames.PU_LOCATION_ID, EntityNames.PU_LOCATION_ID),
      Map.entry(ColumnNames.DO_LOCATION_ID, EntityNames.DO_LOCATION_ID),
      Map.entry(ColumnNames.PASSENGER_COUNT, EntityNames.PASSENGER_COUNT),
      Map.entry(ColumnNames.TRIP_DISTANCE, EntityNames.TRIP_DISTANCE),
      Map.entry(ColumnNames.FARE_AMOUNT, EntityNames.FARE_AMOUNT),
      Map.entry(ColumnNames.EXTRA, EntityNames.EXTRA),
      Map.entry(ColumnNames.MTA_TAX, EntityNames.MTA_TAX),
      Map.entry(ColumnNames.TIP_AMOUNT, EntityNames.TIP_AMOUNT),
      Map.entry(ColumnNames.TOLLS_AMOUNT, EntityNames.TOLLS_AMOUNT),
      Map.entry(ColumnNames.EHAIL_FEE, EntityNames.EHAIL_FEE),
      Map.entry(ColumnNames.IMPROVEMENT_SURCHARGE, EntityNames.IMPROVEMENT_SURCHARGE),
      Map.entry(ColumnNames.TOTAL_AMOUNT, EntityNames.TOTAL_AMOUNT),
      Map.entry(ColumnNames.TRIP_TYPE, EntityNames.TRIP_TYPE),
      Map.entry(ColumnNames.PAYMENT_TYPE, EntityNames.PAYMENT_TYPE),
      Map.entry(ColumnNames.CONGESTION_SURCHARGE, EntityNames.CONGESTION_SURCHARGE)
  );


}
