package metrics;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;
import java.util.List;


public class DataStructureMetric implements Metric<String> {
    
private static boolean hasPrinted = false;

    private final String baseDir = "src/fraud";

    @Override
    public String getName() {
        return "Data Structure Complexity";
    }

@Override
public double measure(List<String> dummy) { 
    List<String> ignored = dummy; 
  
        int arrays = 0;

        try {

            for (Path p : Files.walk(Paths.get(baseDir)).toList()) {

                if (!p.toString().endsWith(".java"))
                    continue;

                try (Stream<String> lines = Files.lines(p)) {

                    for (String line : (Iterable<String>) lines::iterator) {

                        if (line.contains("[]"))
                            arrays++;

                    }

                }

            }

        } catch (IOException e) {}

if (!hasPrinted) {
    hasPrinted = true;
    System.out.println("\nData Structure Usage");
    System.out.println("Arrays detected: " + arrays);
}


        return arrays;
    }
}