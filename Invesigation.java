import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.text.DecimalFormat;
/**
 * WEEK 5: EMPIRICAL INVESTIGATION
 * A/B Test of Two Algorithms for Fraud Detection
 * RAW DATA and VALIDITY proof!
 */
public class Invesigation {
        public static class Transaction {
            String id;
            double amount;
            String location;  // "Local" or "Foreign"

            Transaction(String id, double amount, String location) {
                this.id = id;
                this.amount = amount;
                this.location = location;
            }
        }

        // ALGORITHM A: FOR LOOP (Traditional)
        public static class AlgorithmA {
            public static Result detectFraud(List<Transaction> transactions, double threshold) {
                List<Transaction> fraud = new ArrayList<>();
                int comparisons = 0;
        // Capture the start time in  nanoseconds
                long startTime = System.nanoTime();

                // THE FOR LOOP
                for(int i = 0; i < transactions.size(); i++) {
                    Transaction t = transactions.get(i);
                    comparisons++;

                    if(t.amount > threshold && t.location.equals("Foreign")) {
                        fraud.add(t);
                    }
                }
                // Capture the end time in  nanoseconds
                long endTime = System.nanoTime();
                //Calculate the duration in milliseconds
                double timeMs = (endTime - startTime) / 1_000_000.0;

                return new Result("FOR Loop", timeMs, comparisons, fraud.size());
            }
        }

        // ALGORITHM B: WHILE LOOP (Alternative)
        public static class AlgorithmB {
            public static Result detectFraud(List<Transaction> transactions, double threshold) {
                List<Transaction> fraud = new ArrayList<>();
                int comparisons = 0;
                int i = 0;
                // Capture the start time in  nanoseconds
                long startTime = System.nanoTime();

                // THE WHILE LOOP
                while(i < transactions.size()) {
                    Transaction t = transactions.get(i);
                    comparisons++;

                    if(t.amount > threshold && t.location.equals("Foreign")) {
                        fraud.add(t);
                    }
                    i++;
                }
                // Capture the end time in  nanoseconds
                long endTime = System.nanoTime();
                // Calculate the duration in milliseconds
                double timeMs = (endTime - startTime) / 1_000_000.0;

                return new Result("WHILE Loop", timeMs, comparisons, fraud.size());
            }
        }

        // RESULT CLASS
        public static class Result {
            String algorithm;
            double timeMs;
            int comparisons;
            int fraudFound;

            Result(String algo, double time, int comp, int fraud) {
                this.algorithm = algo;
                this.timeMs = time;
                this.comparisons = comp;
                this.fraudFound = fraud;
            }
        }

        // GENERATE TEST DATA
        static List<Transaction> generateTransactions(int count) {
            List<Transaction> list = new ArrayList<>();
            Random rand = new Random();
            String[] locations = {"Local", "Foreign"};

            for(int i = 0; i < count; i++) {
                list.add(new Transaction(
                        "T" + (i+1),
                        1000 + rand.nextDouble() * 5000000,//[1000.0,5001000.0)
                        locations[rand.nextInt(2)]//0 or 1
                ));
            }
            return list;
        }

        // CALCULATE STATISTICS
        public static class Statistics {
            double mean;
            double stdDev;
            double min;
            double max;

            Statistics(List<Double> values) {
                // Calculate mean
                this.mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(0);

                // Calculate min/max
                this.min = values.stream().mapToDouble(Double::doubleValue).min().orElse(0);
                this.max = values.stream().mapToDouble(Double::doubleValue).max().orElse(0);

                // Calculate standard deviation
                double sum = 0;
                for(double v : values) {
                    sum += Math.pow(v - mean, 2);
                }
                this.stdDev = Math.sqrt(sum / values.size());
            }
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            DecimalFormat df = new DecimalFormat("#.####");
            DecimalFormat dfInt = new DecimalFormat("#,###");

            System.out.println("\n" + "=".repeat(70));
            System.out.println("🔬 WEEK 5: EMPIRICAL INVESTIGATION - FRAUD DETECTION A/B TEST");
            System.out.println("=".repeat(70));
            System.out.println("Cyber Security: Banking Fraud Detection System");
            System.out.println("-".repeat(70));

            // Get user input
            System.out.println("\n📋 EXPERIMENT CONFIGURATION");
            System.out.print("   Enter number of transactions to test: ");
            int dataSize = scanner.nextInt();

            System.out.print("   Enter number of test runs (recommend 10-20): ");
            int numRuns = scanner.nextInt();

            double threshold = 1000000; // UGX 1M

            System.out.println("\n⚙️  EXPERIMENT SETUP:");
            System.out.println("   • Algorithm A: FOR Loop (Traditional)");
            System.out.println("   • Algorithm B: WHILE Loop (Alternative)");
            System.out.println("   • Data Size: " + dfInt.format(dataSize) + " transactions per run");
            System.out.println("   • Number of runs: " + numRuns);
            System.out.println("   • Fraud threshold: UGX " + dfInt.format(threshold));
            System.out.println("   • Controlled variable: SAME data for both algorithms");

            System.out.println("\n🔄 Running experiment...\n");

            // Store results
            List<Double> timesA = new ArrayList<>();
            List<Double> timesB = new ArrayList<>();
            List<Integer> fraudCounts = new ArrayList<>();

            // TABLE HEADER
            System.out.println("-".repeat(80));
            System.out.printf("| %-4s | %-15s | %-15s | %-10s | %-12s | %-8s |\n",
                    "Run", "Algorithm A (ms)", "Algorithm B (ms)", "Winner", "Difference", "Fraud");
            System.out.println("-".repeat(80));

            // RUN EXPERIMENT
            for(int run = 1; run <= numRuns; run++) {
                // Generate SAME data for both algorithms (CONTROLLED VARIABLE)
                List<Transaction> testData = generateTransactions(dataSize);

                // Run Algorithm A (FOR Loop)
                Result resultA = AlgorithmA.detectFraud(testData, threshold);
                timesA.add(resultA.timeMs);

                // Run Algorithm B (WHILE Loop)
                Result resultB = AlgorithmB.detectFraud(testData, threshold);
                timesB.add(resultB.timeMs);

                fraudCounts.add(resultA.fraudFound); // Both find same number

                // Determine winner
                String winner;
                double diff;
                if(resultA.timeMs < resultB.timeMs) {
                    winner = "FOR Loop";
                    diff = resultB.timeMs - resultA.timeMs;
                } else {
                    winner = "WHILE Loop";
                    diff = resultA.timeMs - resultB.timeMs;
                }

                // Print row
                System.out.printf("| %-4d | %-15s | %-15s | %-10s | %-12s | %-8d |\n",
                        run,
                        df.format(resultA.timeMs),
                        df.format(resultB.timeMs),
                        winner,
                        df.format(diff) + " ms",
                        resultA.fraudFound
                );

            }

            System.out.println("-".repeat(80));

            // STATISTICAL ANALYSIS
            Statistics statsA = new Statistics(timesA);
            Statistics statsB = new Statistics(timesB);

            double avgA = statsA.mean;
            double avgB = statsB.mean;
            double improvement = Math.abs(avgA - avgB);
            double percentFaster = (improvement / Math.max(avgA, avgB)) * 100;
            String fasterAlgo = (avgA < avgB) ? "FOR Loop" : "WHILE Loop";

            // T-Test for significance
            double tStat = Math.abs(avgA - avgB) /
                    Math.sqrt((statsA.stdDev*statsA.stdDev/numRuns) +
                            (statsB.stdDev*statsB.stdDev/numRuns));
            boolean significant = tStat > 2.0;

            JOptionPane.showMessageDialog(null,"Algorithm A (FOR Loop):" +
                            "\n    Mean : " +df.format(avgA)+
                            "\n    Std Dev :" +df.format(statsA.stdDev)+
                            "\n    Min : " +df.format(statsA.min)+
                            "\n    Max : " +df.format(statsA.max)+
                            "\n Algorithm B (WHILE Loop):"+
                            "\n    Mean : " +df.format(avgA)+
                            "\n    Std Dev : " +df.format(statsB.stdDev)+
                            "\n    Min : " +df.format(statsB.min)+
                            "\n    Max : " +df.format(statsB.max)+
                            "\n HYPOTHESIS TEST:"+
                            "\n    H0: No difference between algorithms"+
                            "\n    H1: Algorithms have different performance"+
                            "\n    t-statistic: "+ String.format("%.4f",tStat)+
                            "\n    Critical value (95%% confidence): 2.0"+
                            "\n    Result: " +(significant ? "REJECT H0" : "FAIL TO REJECT H0")
                    , "STATISTICAL ANALYSIS:",JOptionPane.INFORMATION_MESSAGE);

            // FINAL CONCLUSION
            JOptionPane.showMessageDialog(null,"WINNER: " +
                    fasterAlgo +String.format("%.2f%%",percentFaster)+" faster than "+(fasterAlgo.equals("FOR Loop") ? "WHILE Loop" : "FOR Loop") +
                    "Average speed difference: "+df.format(improvement),"EMPIRICAL INVESTIGATION CONCLUSION",JOptionPane.INFORMATION_MESSAGE);

            if(significant) {
                System.out.println("   • This result is STATISTICALLY SIGNIFICANT (p < 0.05)");
            } else {
                System.out.println("   • This result is NOT statistically significant");
                System.out.println("   • Try increasing number of runs for more confidence");
            }

             //FRAUD DETECTION RATE
            double avgFraud = fraudCounts.stream().mapToInt(Integer::intValue).average().orElse(0);
            double fraudRate = (avgFraud / dataSize) * 100;
            JOptionPane.showMessageDialog(null,"Average fraud cases found: " +
                    String.format("%.0f",avgFraud) +" out of "+dfInt.format(dataSize)+" " +String.format("(%.2f%%)",fraudRate)
                    ,"FRAUD DETECTION RATE:",JOptionPane.INFORMATION_MESSAGE);

            // VALIDITY EVIDENCE
            JOptionPane.showMessageDialog(null,"1.INTERNAL VALIDITY:" +
                            "\n    Controlled variable: SAME transaction data for both algorithms"+
                            "\n    Controlled environment: Same machine, same JVM"+
                            "\n    Multiple runs: " + numRuns + " repetitions"+
                            "\n 2.EXTERNAL VALIDITY:"+
                            "\n    Tested with "+dataSize+ " transactions"+
                            "\n    Random data generation simulates real transactions" +
                            "\n 3.STATISTICAL VALIDITY:"+
                            "\n    Standard deviations: "+ "A= "+String.format("%.4f",statsA.stdDev)+", "+"B= "+String.format("%.4f",statsB.stdDev) +"(low = consistent)"+
                            "\n    Confidence level: " +(significant ? " 95% (statistically significant)" : " Needs more runs")
                    , "VALIDITY EVIDENCE",JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(null,"Algorithm A times: " +
                    timesA.toString() +"\nAlgorithm B times:" +
                    timesB.toString(), "RAW DATA SUMMARY:",JOptionPane.INFORMATION_MESSAGE);

            // RECOMMENDATION
            if(avgA < avgB) {
                JOptionPane.showMessageDialog(null,"Use FOR Loop algorithm for fraud detection:Faster by" +
                        df.format(improvement) +"ms average Would process " +dfInt.format((long)(1000/avgA * dataSize)) +
                        " transactions/sec","RECOMMENDATION FOR BANKING SYSTEM:",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,"Use WHILE Loop algorithm for fraud detection:" +
                        df.format(improvement) +"ms average Would process " + dfInt.format((long)(1000/avgA * dataSize)) +
                        " transactions/sec","RECOMMENDATION FOR BANKING SYSTEM:",JOptionPane.INFORMATION_MESSAGE);
            }

            System.out.println("\n" + "*".repeat(70));
            System.out.println(" END OF EMPIRICAL INVESTIGATION");
            System.out.println("*".repeat(70));

            scanner.close();
        }

}
