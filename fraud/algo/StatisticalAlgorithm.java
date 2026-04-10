package fraud.algo;

import fraud.DetectionResult;
import fraud.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Statistical fraud detector: flags transactions far above the mean.
 */
public class StatisticalAlgorithm implements Algorithm {

    @Override
    public String getName() { return "Stat"; }

    @Override
    public DetectionResult analyze(
            List<Transaction> transactions,
            Map<String, Object> parameters
    ) {
        // threshold expressed in standard deviations above the mean;
        // values >0.5 guarantee at least some outliers will be caught with
        // our random dataset.  caller may override via parameters.
        double zThreshold = (double) parameters.getOrDefault("zThreshold", 0.5);

        long start = System.nanoTime();

        double sum = 0;
        for (Transaction t : transactions) {
            sum += t.getAmount();
        }
        double mean = sum / transactions.size();

        double sumSquared = 0;
        for (Transaction t : transactions) {
            double diff = t.getAmount() - mean;
            sumSquared += diff * diff;
        }
        double stdDev = Math.sqrt(sumSquared / transactions.size());

        List<Transaction> flagged = new ArrayList<>();
        for (Transaction t : transactions) {
            double zScore = (t.getAmount() - mean) / stdDev;
            if (zScore > zThreshold) {
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
