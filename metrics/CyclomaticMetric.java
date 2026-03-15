package metrics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class CyclomaticMetric implements Metric<String> {

    private final String targetFile = "src/fraud/algo/SimpleRuleAlgorithm.java";

    @Override
    public String getName() {
        return "Cyclomatic Complexity";
    }

@Override
public double measure(List<String> dummy) {
    List<String> ignored = dummy;  // Ignoring parameter

        int decisions = 0;

        try {

            for (String line : Files.readAllLines(Paths.get(targetFile))) {

                line = line.trim();

                if (line.contains("else if")) decisions++;
                else if (line.contains("if(") || line.contains("if (")) decisions++;

                if (line.contains("for(") || line.contains("for (")) decisions++;

                if (line.contains("while(") || line.contains("while (")) decisions++;

                if (line.contains("switch(") || line.contains("switch (")) decisions++;

                if (line.contains("case ")) decisions++;

                if (line.contains("default ")) decisions++;

                if (line.contains("&&") || line.contains("||")) decisions++;

            }

        } catch (IOException e) {}

        return 1 + decisions;
    }
}