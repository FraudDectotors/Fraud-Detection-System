package metrics;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;


public class SizeMetric implements Metric<String> {

    private final String baseDir = ".";

    private int loc = 0;
    private int comments = 0;
    private int code = 0;
    private int classes = 0;
    private int methods = 0;

    @Override
    public String getName() {
        return "Software Size";
    }

   @Override
public double measure(List<String> dummy) {

        try {

            Files.walk(Paths.get(baseDir))
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(this::analyzeFile);

        } catch (IOException ignored) {}

        loc = comments + code;

        double commentDensity = loc == 0 ? 0 : (double) comments / loc;

        return loc;
    }

    private void analyzeFile(Path path) {

        try (Stream<String> lines = Files.lines(path)) {

            lines.forEach(line -> {

                String l = line.trim();

                if (l.startsWith("//"))
                    comments++;

                else if (!l.isEmpty())
                    code++;

                if (l.contains("class "))
                    classes++;

                if (l.contains("(") && l.contains(")") && l.contains("{"))
                    methods++;

            });

        } catch (IOException ignored) {}
    }
}
