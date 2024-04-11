package ai.cognitiv.taxiparsing.reading.conversion;

import ai.cognitiv.taxiparsing.reading.Converter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Map;

public class ConvertersFactory {

  private static final int ZERO_X_PREFIX_LEN = 2;
  private static final double DEFAULT_PAYMENT_TYPE_PLACEHOLDER = 999D;


  private static final Map<String, Converter> GREEN_CONVERTERS_MAPPING = Map.ofEntries(
      Map.entry(GreenTripRecord.ColumnNames.VENDOR_ID, ConvertersFactory.getObjectToLongConverter()),
      Map.entry(GreenTripRecord.ColumnNames.LPEP_PICKUP_DATETIME, ConvertersFactory.getObjectToLocalDateTimeConverter()),
      Map.entry(GreenTripRecord.ColumnNames.LPEP_DROPOFF_DATETIME, ConvertersFactory.getObjectToLocalDateTimeConverter()),
      Map.entry(GreenTripRecord.ColumnNames.STORE_AND_FWD_FLAG, ConvertersFactory.getObjectToStringConverter()),
      Map.entry(GreenTripRecord.ColumnNames.RATECODE_ID, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.PU_LOCATION_ID, ConvertersFactory.getObjectToLongConverter()),
      Map.entry(GreenTripRecord.ColumnNames.DO_LOCATION_ID, ConvertersFactory.getObjectToLongConverter()),
      Map.entry(GreenTripRecord.ColumnNames.PASSENGER_COUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.TRIP_DISTANCE, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.FARE_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.EXTRA, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.MTA_TAX, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.TIP_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.TOLLS_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.EHAIL_FEE, ConvertersFactory.getObjectToIntConverter()),
      Map.entry(GreenTripRecord.ColumnNames.IMPROVEMENT_SURCHARGE, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.TOTAL_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.PAYMENT_TYPE, ConvertersFactory.getObjectToDoubleConverterOrDefaultForGroupingKey()),
      Map.entry(GreenTripRecord.ColumnNames.TRIP_TYPE, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(GreenTripRecord.ColumnNames.CONGESTION_SURCHARGE, ConvertersFactory.getObjectToDoubleConverter())
  );

  private static final Map<String, Converter> YELLOW_CONVERTERS_MAPPING = Map.ofEntries(
      Map.entry(YellowTripRecord.ColumnNames.VENDOR_ID, ConvertersFactory.getObjectToLongConverter()),
      Map.entry(YellowTripRecord.ColumnNames.TPEP_PICKUP_DATETIME, ConvertersFactory.getObjectToLocalDateTimeConverter()),
      Map.entry(YellowTripRecord.ColumnNames.TPEP_DROPOFF_DATETIME, ConvertersFactory.getObjectToLocalDateTimeConverter()),
      Map.entry(YellowTripRecord.ColumnNames.PASSENGER_COUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.TRIP_DISTANCE, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.RATECODEID, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.STORE_AND_FWD_FLAG, ConvertersFactory.getObjectToStringConverter()),
      Map.entry(YellowTripRecord.ColumnNames.PULOCATIONID, ConvertersFactory.getObjectToLongConverter()),
      Map.entry(YellowTripRecord.ColumnNames.DOLOCATIONID, ConvertersFactory.getObjectToLongConverter()),
      Map.entry(YellowTripRecord.ColumnNames.PAYMENT_TYPE, ConvertersFactory.getObjectToLongConverterOrDefaultForGroupingKey()),
      Map.entry(YellowTripRecord.ColumnNames.FARE_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.EXTRA, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.MTA_TAX, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.TIP_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.TOLLS_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.IMPROVEMENT_SURCHARGE, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.TOTAL_AMOUNT, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.CONGESTION_SURCHARGE, ConvertersFactory.getObjectToDoubleConverter()),
      Map.entry(YellowTripRecord.ColumnNames.AIRPORT_FEE, ConvertersFactory.getObjectToDoubleConverter())
  );

  private static Map<Class<?>, Map<String, Converter>> MAPPINGS = Map.ofEntries(
      Map.entry(GreenTripRecord.class, GREEN_CONVERTERS_MAPPING),
      Map.entry(YellowTripRecord.class, YELLOW_CONVERTERS_MAPPING)
  );

  private static Map<Class<?>, Converter> TO_RECORD_MAPPING = Map.ofEntries(
      Map.entry(GreenTripRecord.class, new GreenTripConverter()),
      Map.entry(YellowTripRecord.class, new YellowTripConverter())
  );

  public <S> Converter<Object, Object> getMapping(Class<S> clazz, String columnName) {
    return MAPPINGS.get(clazz).get(columnName.toLowerCase(Locale.ROOT));
  }

  public <T> Converter<Map<String, Object>, T> getConverter(Class<T> r) {
    return TO_RECORD_MAPPING.get(r);
  }


  private static Converter<Object, Integer> getObjectToIntConverter() {
    return input -> {
      if (input == null) {
        return null;
      }
      if (input.getClass() == Long.class) {
        return ((Long) input).intValue();
      }
      return (Integer) input;
    };
  }

  private static Converter<Object, Long> getObjectToLongConverter() {
    return input -> {
      if (input == null) {
        return null;
      }
      if (input.getClass() == Integer.class) {
        return ((Integer) input).longValue();
      }
      return (Long) input;
    };
  }

  private static Converter<Object, Long> getObjectToLongConverterOrDefaultForGroupingKey() {
    return input -> {
      if (input == null) {
        return (Long) Double.valueOf(DEFAULT_PAYMENT_TYPE_PLACEHOLDER).longValue();
      }
      if (input.getClass() == Integer.class) {
        return ((Integer) input).longValue();
      }
      return (Long) input;
    };
  }

  private static Converter<Object, LocalDateTime> getObjectToLocalDateTimeConverter() {
    return rawHexValue -> {
      if (rawHexValue == null) {
        return null;
      }
      if (rawHexValue.getClass() == Long.class) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(((Long) rawHexValue).longValue() / 1_000_000), ZoneOffset.UTC.normalized());
      }

      byte[] originalParquetByteArray = convertToOriginalArray(String.valueOf(rawHexValue));

      return convertToTimestamp(originalParquetByteArray);
    };
  }

  private static byte[] convertToOriginalArray(String hexString) {
    if (hexString.startsWith("0x")) {
      hexString = hexString.substring(ZERO_X_PREFIX_LEN);
    }

    byte[] byteArray = new byte[hexString.length() / 2];

    // Convert each pair of characters to a byte
    for (int i = 0; i < byteArray.length; i++) {
      int index = i * 2;
      int j = Integer.parseInt(hexString.substring(index, index + 2), 16);
      byteArray[i] = (byte) j;
    }
    return byteArray;
  }

  private static LocalDateTime convertToTimestamp(byte[] byteArray) {
    for (int i = 0; i < byteArray.length; i++) {
      if (byteArray[i] < 0) {
        byteArray[i] += (byte) 256;
      }
    }

    // Create a ByteBuffer and set the byte order
    ByteBuffer buffer = ByteBuffer.wrap(byteArray);
    buffer.order(ByteOrder.LITTLE_ENDIAN);

    // Unpack bytes to nanoseconds and days
    long nanoseconds = buffer.getLong();
    int days = buffer.getInt();

    // Calculate the timestamp
    long timestampSeconds = days * 24L * 60L * 60L - 2440588L * 24L * 60L * 60L;
    long timestampNanos = timestampSeconds * 1000000000L + nanoseconds / 1000L;
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(0L, timestampNanos), ZoneOffset.UTC.normalized());
  }

  private static Converter<Object, Double> getObjectToDoubleConverter() {
    return input -> {
      if (input == null) {
        return null;
      }
      if (input.getClass() == Float.class) {
        return ((Float) input).doubleValue();
      } else if (input.getClass() == Long.class) {
        return ((Long) input).doubleValue();
      } else if (Integer.class == input.getClass()) {
        return ((Integer) input).doubleValue();
      }
      return (Double) input;
    };
  }

  private static Converter<Object, Double> getObjectToDoubleConverterOrDefaultForGroupingKey() {
    return input -> {
      if (input == null) {
        return (Double) DEFAULT_PAYMENT_TYPE_PLACEHOLDER;
      }
      if (input.getClass() == Float.class) {
        return ((Float) input).doubleValue();
      } else if (input.getClass() == Long.class) {
        return ((Long) input).doubleValue();
      } else if (Integer.class == input.getClass()) {
        return ((Integer) input).doubleValue();
      }
      return (Double) input;
    };
  }

  private static Converter<Object, String> getObjectToStringConverter() {
    return input -> {
      if (input == null) {
        return null;
      }
      if (input.getClass() == String.class) {
        return (String) input;
      }
      return String.valueOf(input);
    };
  }

}
