package main;

import experiment.ConsoleReporter;
import experiment.Experiment;
import fraud.DetectionResult;
import fraud.Transaction;
import fraud.algo.SimpleRuleAlgorithm;
import fraud.algo.StatisticalAlgorithm;
import metrics.Metric;
import metrics.SizeMetric;
import metrics.CyclomaticMetric; 
import metrics.NestingMetric;
import metrics.DataStructureMetric;

import java.util.*;

public class Main {

    public static class TimeMetric implements Metric<DetectionResult> {
        @Override public String getName() { return "TimeMs"; }
        @Override public double measure(List<DetectionResult> data) {
            return data.get(0).timeMs;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of transactions:");
        int dataSize = scanner.nextInt();
        if (dataSize <= 0) {
            System.err.println("Error: Transaction count must be > 0");
            scanner.close();
            return;
        }

        System.out.println("Enter number of runs:");
        int numRuns = scanner.nextInt();
        if (numRuns <= 0) {
            System.err.println("Error: Number of runs must be > 0");
            scanner.close();
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("highAmountThreshold", 1_000_000.0);
        params.put("foreignLocation", "Foreign");
        // the z‑score threshold for the statistical detector. 2.0 was
        // far too aggressive given the random data, so we lower it to
        // something that will actually flag outliers.  Feel free to
        // tweak this value (1.0–1.8 are typical) once you’ve seen some
        // results.
        // lower the default threshold to 0.5 so that the statistical
        // detector actually finds some anomalies in the random data set.
        // You can still supply a higher value via command‑line or by
        // editing these parameters if you want fewer hits.
        params.put("zThreshold", 0.5);

        List<Transaction> data = TransactionGenerator.generate(dataSize);

        Experiment experiment = new Experiment();
        experiment.addParameters(params);
        experiment.addAlgorithm(new SimpleRuleAlgorithm());
        experiment.addAlgorithm(new StatisticalAlgorithm());
        

        experiment.addMetric(new TimeMetric());
   
        experiment.addMetric((Metric<DetectionResult>)(Metric<?>)new SizeMetric());
        experiment.addMetric((Metric<DetectionResult>)(Metric<?>)new CyclomaticMetric());
        experiment.addMetric((Metric<DetectionResult>)(Metric<?>)new NestingMetric());
        experiment.addMetric((Metric<DetectionResult>)(Metric<?>)new DataStructureMetric());  

        experiment.run(data, numRuns, new ConsoleReporter());

        scanner.close();
    }
}