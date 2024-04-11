package ai.cognitiv.taxiparsing.comandline;

import ai.cognitiv.taxiparsing.TaxiTripsReportException;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class CommandLineArgsParser implements ArgsParser {

  @Override
  public CommandLineArguments parse(String... args) {
    CommandLineArguments result = new CommandLineArguments();
    JCommander parser = JCommander.newBuilder()
        .addObject(result)
        .build();
    try {
      parser.parse(args);
    } catch (ParameterException ex) {
      if (!result.isHelp()) {
        log.error(ex.getLocalizedMessage());
        parser.usage();
        throw new TaxiTripsReportException(ex.getLocalizedMessage(), ex);
      } else {
        parser.usage();
        return null;
      }
    }
    if (result.isHelp()) {
      parser.usage();
      return null;
    }
    if (CollectionUtils.isEmpty(result.getGreenFilesPaths()) && CollectionUtils.isEmpty(result.getYellowFilesPaths())) {
      log.error("Path to input files is not set! You should specify at least one path!");
      parser.usage();
      throw new TaxiTripsReportException("Path to green and yellow taxi files is not specified!");
    }
    log.info("Input parameters parsed!");
    return result;
  }
}
