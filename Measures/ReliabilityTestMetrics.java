package Measures;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.*;
public class ReliabilityTestMetrics {
        // Store test run results
        static class TestRun {
            int runNumber;
            boolean passed;
            double executionTimeMs;
            String failureReason;

            TestRun(int runNumber, boolean passed, double timeMs, String reason) {
                this.runNumber = runNumber;
                this.passed = passed;
                this.executionTimeMs = timeMs;
                this.failureReason = reason;
            }
        }

        public static void main(String[] args) {

            System.out.println("=" .repeat(70));
            System.out.println("RELIABILITY TEST METRICS ANALYZER");
            System.out.println("=" .repeat(70));
            System.out.println();

            // ===== PART 1: SIMULATE TEST RUNS =====
            System.out.println("--- TEST EXECUTION RESULTS ---");

            List<TestRun> runs = new ArrayList<>();

            // Simulate 20 test runs (you replace with your actual test results)
            // In real testing, you would load this from a log file
            Random rand = new Random(42); // fixed seed for reproducibility

            for (int i = 1; i <= 20; i++) {
                boolean passed = rand.nextDouble() > 0.1; // 90% pass rate
                double timeMs = 1.5 + rand.nextDouble() * 2;
                String reason = passed ? "OK" : "NullPointerException";
                runs.add(new TestRun(i, passed, timeMs, reason));

                String status = passed ? "✓ PASS" : "✗ FAIL";
                System.out.printf("Run %2d: %s (%.2f ms) - %s%n", i, status, timeMs, reason);
            }

            System.out.println();

            // ===== PART 2: CALCULATE RELIABILITY METRICS =====
            System.out.println("--- RELIABILITY METRICS ---");

            int totalRuns = runs.size();
            int failures = (int) runs.stream().filter(r -> !r.passed).count();
            int successes = totalRuns - failures;
            double totalTime = runs.stream().mapToDouble(r -> r.executionTimeMs).sum();

            // Failure Rate (λ)
            double failureRate = (double) failures / totalRuns;

            // Mean Time To Failure (MTTF)
            double mttf = totalTime / failures;
            if (failures == 0) mttf = Double.POSITIVE_INFINITY;

            // Reliability at 10 runs (R = e^(-λ × t))
            double timePeriod = 10.0; // 10 runs
            double reliability = Math.exp(-failureRate * timePeriod);

            System.out.printf("Total Test Runs:        %d%n", totalRuns);
            System.out.printf("Successful Runs:        %d%n", successes);
            System.out.printf("Failed Runs:            %d%n", failures);
            System.out.printf("Failure Rate (λ):        %.4f failures per run%n", failureRate);
            System.out.printf("Mean Time To Failure:    %.2f ms%n", mttf);
            System.out.printf("Reliability (R):         %.2f%% (after %d runs)%n", reliability * 100, (int)timePeriod);

            System.out.println();

            // ===== PART 3: RELIABILITY GROWTH MODEL =====
            System.out.println("--- RELIABILITY GROWTH (Over Time) ---");
            System.out.println("Run Range    Failures    Reliability");
            System.out.println("-".repeat(45));

            for (int block = 1; block <= 4; block++) {
                int start = (block - 1) * 5 + 1;
                int end = block * 5;
                long blockFailures = runs.stream()
                        .filter(r -> r.runNumber >= start && r.runNumber <= end)
                        .filter(r -> !r.passed)
                        .count();

                double blockReliability = 1.0 - (blockFailures / 5.0);
                System.out.printf("Runs %2d-%-2d        %d           %.0f%%%n",
                        start, end, blockFailures, blockReliability * 100);
            }

            System.out.println();

            // ===== PART 4: TEST COVERAGE METRICS =====
            System.out.println("--- TEST COVERAGE METRICS ---");

            int totalMethods = countJavaMethods(Paths.get("").toAbsolutePath().toString());
            int testedMethods = estimateTestedMethods();
            double methodCoverage = (double) testedMethods / totalMethods * 100;

            int totalDecisionPoints = countDecisionPoints(Paths.get("").toAbsolutePath().toString());
            int testedDecisionPoints = estimateTestedDecisionPoints();
            double branchCoverage = (double) testedDecisionPoints / totalDecisionPoints * 100;

            System.out.printf("Total Methods Found:        %d%n", totalMethods);
            System.out.printf("Methods Tested:             %d%n", testedMethods);
            System.out.printf("Method Coverage:            %.1f%%%n", methodCoverage);
            System.out.printf("Total Decision Points:      %d%n", totalDecisionPoints);
            System.out.printf("Decision Points Tested:     %d%n", testedDecisionPoints);
            System.out.printf("Branch Coverage:            %.1f%%%n", branchCoverage);

            System.out.println();

            // ===== PART 5: DEFECT METRICS =====
            System.out.println("--- DEFECT METRICS ---");

            int totalLOC = countTotalLOC(Paths.get("").toAbsolutePath().toString());
            int defectsFound = estimateDefectsFound();
            double defectDensity = (double) defectsFound / (totalLOC / 1000.0);

            System.out.printf("Total Lines of Code:        %d%n", totalLOC);
            System.out.printf("Defects Found:              %d%n", defectsFound);
            System.out.printf("Defect Density:             %.2f defects/KLOC%n", defectDensity);

            System.out.println();

            // ===== PART 6: FINAL VERDICT =====
            System.out.println("--- RELIABILITY ASSESSMENT ---");
            System.out.println("-".repeat(50));

            String grade;
            if (reliability >= 0.95 && defectDensity < 5) {
                grade = "HIGH RELIABILITY ✓ Ready for production";
            } else if (reliability >= 0.80 && defectDensity < 15) {
                grade = "MEDIUM RELIABILITY ⚠ Needs improvement";
            } else {
                grade = "LOW RELIABILITY ✗ Not ready for production";
            }

            System.out.println("Assessment: " + grade);
            System.out.println();
            System.out.println("Recommendations:");
            if (failures > 0) System.out.println("  • Add try-catch blocks to prevent crashes");
            if (branchCoverage < 80) System.out.println("  • Increase test coverage for decision points");
            if (defectDensity > 10) System.out.println("  • Fix critical defects before deployment");
            if (methodCoverage < 70) System.out.println("  • Write tests for untested methods");

            System.out.println();
            System.out.println("=" .repeat(70));
        }

        // ===== HELPER METHODS =====

        private static int countJavaMethods(String folderPath) {
            AtomicInteger count = new AtomicInteger();
            try {
                Files.walk(Paths.get(folderPath))
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".java"))
                        .forEach(file -> {
                            try {
                                String content = new String(Files.readAllBytes(file));
                                Pattern pattern = Pattern.compile("\\s+\\w+\\s+\\w+\\s*\\(.*\\)\\s*\\{");
                                Matcher matcher = pattern.matcher(content);
                                while (matcher.find()) count.getAndIncrement();
                            } catch (Exception e) {}
                        });
            } catch (Exception e) {}
            return count.get();
        }

        private static int countDecisionPoints(String folderPath) {
            AtomicInteger count = new AtomicInteger();
            try {
                Files.walk(Paths.get(folderPath))
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".java"))
                        .forEach(file -> {
                            try {
                                String content = new String(Files.readAllBytes(file));
                                String[] patterns = {"\\bif\\b", "\\bfor\\b", "\\bwhile\\b", "&&", "\\|\\|"};
                                for (String p : patterns) {
                                    Pattern pattern = Pattern.compile(p);
                                    Matcher matcher = pattern.matcher(content);
                                    while (matcher.find()) count.getAndIncrement();
                                }
                            } catch (Exception e) {}
                        });
            } catch (Exception e) {}
            return count.get();
        }

        private static int countTotalLOC(String folderPath) {
            AtomicInteger count = new AtomicInteger();
            try {
                Files.walk(Paths.get(folderPath))
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".java"))
                        .forEach(file -> {
                            try {
                                List<String> lines = Files.readAllLines(file);
                                for (String line : lines) {
                                    if (!line.trim().isEmpty() && !line.trim().startsWith("//")) {
                                        count.getAndIncrement();
                                    }
                                }
                            } catch (Exception e) {}
                        });
            } catch (Exception e) {}
            return count.get();
        }

        // For demo - replace with actual values from your testing
        private static int estimateTestedMethods() {
            return 2; // SimpleRuleAlgorithm and StatisticalAlgorithm
        }

        private static int estimateTestedDecisionPoints() {
            return 5; // Most decision points tested
        }

        private static int estimateDefectsFound() {
            return 0; // Null safety issues, no exception handling
        }

}

