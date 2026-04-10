package fraud.algo;

import fraud.DetectionResult;
import fraud.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rule-based fraud detector: high amount + foreign location.
 */
public class SimpleRuleAlgorithm implements Algorithm {

    @Override
    public String getName() { return "Rule"; }

    @Override
    public DetectionResult analyze(
            List<Transaction> transactions,
            Map<String, Object> parameters
    ) {
        double highAmountThreshold =
            (double) parameters.getOrDefault("highAmountThreshold", 1_000_000.0);

        String foreignLocation =
            (String) parameters.getOrDefault("foreignLocation", "Foreign");

        List<Transaction> flagged = new ArrayList<>();
        long start = System.nanoTime();

        for (Transaction t : transactions) {
            if (t.getAmount() > highAmountThreshold &&
                foreignLocation.equals(t.getLocation())) {
                flagged.add(t);
            }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        return new DetectionResult(
                getName(),
                timeMs,
                flagged.size(),
                flagged
        );
    }
}
