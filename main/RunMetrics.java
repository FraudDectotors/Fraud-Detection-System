package main;

import Measures.ComplexityAnalyzer;
import Measures.CostMetric;
import Measures.ProductAttribute;
import Measures.ReliabilityTestMetrics;
import Measures.SoftwareSize;
import Measures.TestMetricsAnalyzer;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import metrics.OOMetricsSuite;

public class RunMetrics {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println(" FRAUD DETECTION - METRICS DASHBOARD");
                System.out.println("=".repeat(60));
                System.out.println("  [1] : Software Size Metrics (LOC & FP)");
                System.out.println("  [2] : Complexity (Cyclomatic)");
                System.out.println("  [3] : Transact");
                System.out.println("  [4] : TestMetrics");
                System.out.println("  [5] : ReliabilityTestMetrics");
                System.out.println("  [6] : Cost Metric");
                System.out.println("  [7] : Product Attribute");
                System.out.println("  [8] : Object-Oriented Metrics Suite (DIT, NOC, WMC, CBO, RFC, LCOM)");
                System.out.println("  [0] Exit");

                System.out.print("\nEnter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        SoftwareSize.main(new String[0]);
                        break;
                    case 2:
                        ComplexityAnalyzer.main(new String[0]);
                        break;
                    case 3:
                        Main.main(new String[0]);
                        break;
                    case 4:
                        TestMetricsAnalyzer.main(new String[0]);
                        break;
                    case 5:
                        ReliabilityTestMetrics.main(new String[0]);
                        break;
                    case 6:
                        runCostMetric(scanner);
                        break;
                    case 7:
                        runProductAttribute(scanner);
                        break;
                    case 8:
                        System.out.println("\n=== OBJECT-ORIENTED METRICS SUITE ===\n");
                        OOMetricsSuite ooSuite = new OOMetricsSuite();
                        ooSuite.printReport();
                        break;
                    case 0:
                        System.out.println("\nGoodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }

                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nNo more Elements to iterate");
        }
    }

    private static void runCostMetric(Scanner scanner) {
        System.out.print("Development cost: ");
        double developmentCost = scanner.nextDouble();
        System.out.print("Maintenance cost: ");
        double maintenanceCost = scanner.nextDouble();
        System.out.print("Resource cost: ");
        double resourceCost = scanner.nextDouble();
        scanner.nextLine();

        CostMetric costMetric = new CostMetric(developmentCost, maintenanceCost, resourceCost);
        System.out.println("\n" + costMetric);
    }

    private static void runProductAttribute(Scanner scanner) {
        System.out.print("Reliability: ");
        String reliability = scanner.nextLine();
        System.out.print("Performance: ");
        String performance = scanner.nextLine();
        System.out.print("Usability: ");
        String usability = scanner.nextLine();

        ProductAttribute productAttribute = new ProductAttribute(reliability, performance, usability);
        System.out.println("\n" + productAttribute);
    }
}
