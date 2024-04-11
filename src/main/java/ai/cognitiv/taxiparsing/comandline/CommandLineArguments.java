package ai.cognitiv.taxiparsing.comandline;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.CommaParameterSplitter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class CommandLineArguments {


  @Parameter
  private List<String> parameters = new ArrayList<>();

  @Parameter(names = {"--filesGreen", "-fg"}, splitter = CommaParameterSplitter.class, description = "List of files Green Taxi to " +
      "read")
  private List<String> greenFilesPaths;

  @Parameter(names = {"--filesYellow", "-fy"}, splitter = CommaParameterSplitter.class, description = "List of Yellow Taxi files " +
      "to read")
  private List<String> yellowFilesPaths;

  @Parameter(names = {"--start", "-s"}, converter = LocalDateTimeConverter.class, description = "Start date time dimension in format f.e: " +
      "2024-02-16T19:56:35")
  private LocalDateTime startTime;
  @Parameter(names = {"--end", "-e"}, converter = LocalDateTimeConverter.class, description = "End date time dimension in format f.e: " +
      "2024-02-16T19:56:35")
  private LocalDateTime endDateTime;
  @Parameter(names = {"--isGreen", "-g"}, description = "Should read Green taxis (T\\F)")
  private String greenTaxi = "t";
  @Parameter(names = {"--isYellow", "-y"}, description = "Should read Yellow taxis (T\\F)")
  private String yellowTaxi = "t";
  @Parameter(names = {"--vendor", "-v"}, description = "vendorId to filter on")
  private Long vendorId;
  @Parameter(names = {"--pickUp", "-p"}, description = "pickUp location Id")
  private Long pickUpLocationId;
  @Parameter(names = {"--dropOff", "-d"}, description = "Drop off location Id")
  private Long dropOffLocationId;
  @Parameter(names = {"--help", "-h"}, description = "See help menu")
  private boolean help;
}
