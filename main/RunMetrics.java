package main;
import Measures.ComplexityAnalyzer;
import Measures.ReliabilityTestMetrics;
import Measures.SoftwareSize;
import Measures.TestMetricsAnalyzer;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
                    System.out.println("  [0] Exit");

                    System.out.print("\nEnter choice: ");
                    int choice = scanner.nextInt();

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
                        case 0:
                            System.out.println("\nGoodbye!");
                            return;
                        default:
                            System.out.println("Invalid choice!");
                    }

                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                }
            }catch(NoSuchElementException e){
                System.out.println("\nNo more Elements to iterate");
            }
        }

}
