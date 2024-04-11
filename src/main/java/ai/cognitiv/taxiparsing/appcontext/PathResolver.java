package ai.cognitiv.taxiparsing.appcontext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PathResolver {

  List<String> resolvePaths(Boolean enabled, List<String> inputPath, String[] defaultPaths) {
    if (Boolean.FALSE.equals(enabled)) {
      return List.of();
    }
    return Optional.ofNullable(inputPath)
        .orElse(Arrays.asList(defaultPaths));
  }

}
