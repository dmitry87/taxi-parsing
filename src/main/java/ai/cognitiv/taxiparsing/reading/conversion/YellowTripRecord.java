package ai.cognitiv.taxiparsing.reading.conversion;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class YellowTripRecord {

  Long vendorID;
  LocalDateTime tpepPickUpDatetime;
  LocalDateTime tpepDropOffDatetime;
  Double passengerCount;
  Double tripDistance;
  Double rateCodeId;
  String storeAndFwdFlag;
  Long pickUpLocationId;
  Long dropOffLocationId;
  Long paymentType;
  Double fareAmount;
  Double extra;
  Double mtaTax;
  Double tipAmount;
  Double tollsAmount;
  Double improvementSurcharge;
  Double totalAmount;
  Double congestionSurcharge;
  Double airportFee;

  private static final Map<String, String> MAPPING = Map.ofEntries(
      Map.entry(ColumnNames.VENDOR_ID, EntityNames.VENDORID),
      Map.entry(ColumnNames.TPEP_PICKUP_DATETIME, EntityNames.TPEP_PICKUP_DATETIME),
      Map.entry(ColumnNames.TPEP_DROPOFF_DATETIME, EntityNames.TPEP_DROPOFF_DATETIME),
      Map.entry(ColumnNames.PASSENGER_COUNT, EntityNames.PASSENGER_COUNT),
      Map.entry(ColumnNames.TRIP_DISTANCE, EntityNames.TRIP_DISTANCE),
      Map.entry(ColumnNames.RATECODEID, EntityNames.RATECODEID),
      Map.entry(ColumnNames.STORE_AND_FWD_FLAG, EntityNames.STORE_AND_FWD_FLAG),
      Map.entry(ColumnNames.PULOCATIONID, EntityNames.PULOCATIONID),
      Map.entry(ColumnNames.DOLOCATIONID, EntityNames.DOLOCATIONID),
      Map.entry(ColumnNames.PAYMENT_TYPE, EntityNames.PAYMENT_TYPE),
      Map.entry(ColumnNames.FARE_AMOUNT, EntityNames.FARE_AMOUNT),
      Map.entry(ColumnNames.EXTRA, EntityNames.EXTRA),
      Map.entry(ColumnNames.MTA_TAX, EntityNames.MTA_TAX),
      Map.entry(ColumnNames.TIP_AMOUNT, EntityNames.TIP_AMOUNT),
      Map.entry(ColumnNames.TOLLS_AMOUNT, EntityNames.TOLLS_AMOUNT),
      Map.entry(ColumnNames.IMPROVEMENT_SURCHARGE, EntityNames.IMPROVEMENT_SURCHARGE),
      Map.entry(ColumnNames.TOTAL_AMOUNT, EntityNames.TOTAL_AMOUNT),
      Map.entry(ColumnNames.CONGESTION_SURCHARGE, EntityNames.CONGESTION_SURCHARGE),
      Map.entry(ColumnNames.AIRPORT_FEE, EntityNames.AIRPORT_FEE)
  );


  static class ColumnNames {

    static final String VENDOR_ID = "vendorid";
    static final String TPEP_PICKUP_DATETIME = "tpep_pickup_datetime";
    static final String TPEP_DROPOFF_DATETIME = "tpep_dropoff_datetime";
    static final String PASSENGER_COUNT = "passenger_count";
    static final String TRIP_DISTANCE = "trip_distance";
    static final String RATECODEID = "ratecodeid";
    static final String STORE_AND_FWD_FLAG = "store_and_fwd_flag";
    static final String PULOCATIONID = "pulocationid";
    static final String DOLOCATIONID = "dolocationid";
    static final String PAYMENT_TYPE = "payment_type";
    static final String FARE_AMOUNT = "fare_amount";
    static final String EXTRA = "extra";
    static final String MTA_TAX = "mta_tax";
    static final String TIP_AMOUNT = "tip_amount";
    static final String TOLLS_AMOUNT = "tolls_amount";
    static final String IMPROVEMENT_SURCHARGE = "improvement_surcharge";
    static final String TOTAL_AMOUNT = "total_amount";
    static final String CONGESTION_SURCHARGE = "congestion_surcharge";
    static final String AIRPORT_FEE = "airport_fee";
  }


  private static class EntityNames {

    private EntityNames() {
    }

    public static final String VENDORID = "vendorID";
    public static final String TPEP_PICKUP_DATETIME = "tpepPickUpDatetime";
    public static final String TPEP_DROPOFF_DATETIME = "tpepDropOffDatetime";
    public static final String PASSENGER_COUNT = "passengerCount";
    public static final String TRIP_DISTANCE = "tripDistance";
    public static final String RATECODEID = "rateCodeId";
    public static final String STORE_AND_FWD_FLAG = "storeAndFwdFlag";
    public static final String PULOCATIONID = "pickUpLocationId";
    public static final String DOLOCATIONID = "dropOffLocationId";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String FARE_AMOUNT = "fareAmount";
    public static final String EXTRA = "extra";
    public static final String MTA_TAX = "mtaTax";
    public static final String TIP_AMOUNT = "tipAmount";
    public static final String TOLLS_AMOUNT = "tollsAmount";
    public static final String IMPROVEMENT_SURCHARGE = "improvementSurcharge";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String CONGESTION_SURCHARGE = "congestionSurcharge";
    public static final String AIRPORT_FEE = "airportFee";

  }

}
