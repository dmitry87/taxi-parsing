package ai.cognitiv.taxiparsing.config;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ConfigurationParams {

  List<String> yellowTaxisPaths;
  List<String> greenTaxisPaths;
  DimensionsConfig dimensionsConfig;



}
