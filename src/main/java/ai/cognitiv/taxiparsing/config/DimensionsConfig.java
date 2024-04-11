package ai.cognitiv.taxiparsing.config;

import ai.cognitiv.taxiparsing.comandline.CommandLineArguments;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import lombok.Value;

@Value
public class DimensionsConfig {

  public DimensionsConfig(CommandLineArguments args) {

    start = args.getStartTime();
    end = args.getEndDateTime();
    this.pickUpLocationId = args.getPickUpLocationId();
    this.dropOffLocationId = args.getDropOffLocationId();
    this.vendorId = args.getVendorId();
    this.greenTaxi = !Objects.isNull(args.getGreenTaxi()) && "t".equals(args.getGreenTaxi().toLowerCase(Locale.ROOT));
    this.yellowTaxi = !Objects.isNull(args.getYellowTaxi()) && "t".equals(args.getYellowTaxi().toLowerCase(Locale.ROOT));
  }

  LocalDateTime start;
  LocalDateTime end;
  Long pickUpLocationId;
  Long dropOffLocationId;
  Long vendorId;
  boolean greenTaxi;
  boolean yellowTaxi;

}
