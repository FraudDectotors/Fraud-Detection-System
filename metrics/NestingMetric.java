package metrics;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;


public class NestingMetric implements Metric<String> {

    private final String baseDir = ".";

    @Override
    public String getName() {
        return "Nesting Depth";
    }

@Override
public double measure(List<String> dummy) {
    List<String> ignored = dummy; 

        int globalMaxDepth = 0;

        try {

            for (Path p : Files.walk(Paths.get(baseDir)).toList()) {

                if (!p.toString().endsWith(".java"))
                    continue;

                int depth = 0;
                int maxDepth = 0;

                for (String line : Files.readAllLines(p)) {

                    if (line.contains("{")) {
                        depth++;
                        if (depth > maxDepth)
                            maxDepth = depth;
                    }

                    if (line.contains("}"))
                        depth--;
                }

                if (maxDepth > globalMaxDepth)
                    globalMaxDepth = maxDepth;

            }

        } catch (IOException e) {
            System.err.println("Error analyzing nesting depth: " + e.getMessage());
        }

        return globalMaxDepth;
    }
}
