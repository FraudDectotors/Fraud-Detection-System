package quality;

import metrics.Metric;

public class QualityAnalyzer {
    private Metric[] metrics;

    public QualityAnalyzer(Metric[] metrics) {
        this.metrics = metrics;
    }

    public void analyzeQuality() {
        for (Metric metric : metrics) {
            System.out.println("Analyzing metric: " + metric.getClass().getSimpleName());
            // Implement analysis logic here
        }
    }

    public void reportQuality() {
        // Implement reporting logic here
    }
}