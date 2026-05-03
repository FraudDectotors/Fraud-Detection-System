package metrics;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;


public class SizeMetric implements Metric<String> {

    private static boolean hasPrinted = false;

    private final String baseDir = "fraud";

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

        } catch (IOException e) {
            System.err.println("Error analyzing files in " + baseDir + ": " + e.getMessage());
        }

        loc = comments + code;

        double commentDensity = loc == 0 ? 0 : (double) comments / loc;
// Print ONLY ONCE - first run only
if (!hasPrinted) {
    hasPrinted = true;// Only first time
       System.out.println("\n--- Software Size Metrics (ONCE) ---");
       System.out.println("LOC: " + (comments + code));
       System.out.println("NCLOC: " + code);
       System.out.println("CLOC: " + comments);
       System.out.println("Comment Density: " + (loc == 0 ? 0 : (double)comments/loc));
       System.out.println("Classes: " + classes);
       System.out.println("Methods: " + methods);
}

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

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
