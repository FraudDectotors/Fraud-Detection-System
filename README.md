# Fraud Detection System – Software Metrics Project

## Course: SWE 2204 – Software Metrics  
## Instructor: Dr. Richard Kimera  

---

## 👥 Group 3 Members

| #  | Name                      | RegNo           |
|----|---------------------------|-----------------|
| 1  | SENTONGO JOSEPH MARK      | 2024/BSE/175/PS |
| 2  | MUTASIMU ALI              | 2024/BSE/121/PS |
| 3  | KATO HAMUZAH KIZITO       | 2024/BSE/079/PS |
| 4  | IMAMUT JULIANA            | 2024/BSE/066/PS |
| 5  | AINEMBABAZI FELIX         | 2024/BSE/009/PS |
| 6  | NALUKWAGO HADIJAH         | 2024/BSE/138/PS |
| 7  | MUHUMUZA ISAIAH           | 2024/BSE/110/PS |
| 8  | AKANTORANA TRUST          | 2023/BSE/159/PS |


##  Project Overview

This project implements a **fraud-detection system written in Java** for our Software Metrics coursework. The system consists of two main components:

### Fraud Detection System
- Generates realistic bank-style transactions (with ID, amount in UGX, and location).
- Applies different fraud-detection strategies to detect suspicious transactions.
- Measures performance and behavior using metrics such as **execution time** and **number of fraud cases found**.

Instead of comparing two identical loops, our current version uses **two different fraud-detection strategies**:
- A **rule-based** algorithm that flags high-amount foreign transactions.
- A **statistical** algorithm that flags transactions that are too far from the average amount (z-score based).

### Software Metrics Analysis
- Implements various software metrics to analyze the codebase.
- Includes complexity analysis, cost estimation, quality assessment, and reliability testing.
- Provides a metrics dashboard for running different analyses on the project itself.

The project demonstrates the application of software measurement principles to both a practical system and its own codebase.

##  Code Structure

The project is organized as a **multi‑file, modular Java project** with clear packages:

## Project Structure

The project is organized into the following packages and folders:

- **fraud/**: Contains the transaction model and fraud-detection algorithms.
  - `Transaction.java`: Represents a bank transaction with ID, amount, and location.
  - `DetectionResult.java`: Holds the results of fraud detection, including execution time and flagged transactions.
  - **algo/**: Fraud detection algorithms.
    - `Algorithm.java`: Interface for fraud detection algorithms.
    - `SimpleRuleAlgorithm.java`: Rule-based algorithm that flags high-amount foreign transactions.
    - `StatisticalAlgorithm.java`: Statistical algorithm using z-score to detect outliers.

- **experiment/**: Handles running experiments and reporting results.
  - `Experiment.java`: Runs multiple experiments comparing algorithms.
  - `ExperimentReporter.java`: Interface for reporting experiment results.
  - `ConsoleReporter.java`: Console-based reporter for experiment results.

- **metrics/**: Defines metrics for measuring algorithm performance.
  - `Metric.java`: Generic interface for metrics.
  - `CyclomaticMetric.java`: Measures cyclomatic complexity.
  - `DataStructureMetric.java`: Measures data structure usage.
  - `MeasurementContext.java`: Context for measurements.
  - `NestingMetric.java`: Measures nesting levels.
  - `SizeMetric.java`: Measures code size.

- **main/**: Entry points and utilities.
  - `Main.java`: Main entry point for the fraud detection system.
  - `RunMetrics.java`: Dashboard for running software metrics analyses.
  - `TransactionGenerator.java`: Generates synthetic transaction data.

- **Measures/**: Software metrics analysis tools.
  - `ComplexityAnalyzer.java`: Analyzes cyclomatic complexity of Java files.
  - `CostMetric.java`: Models software development costs.
  - `ProductAttribute.java`: Defines product attributes for metrics.
  - `QualityAnalyzer.java`: Analyzes software quality using metrics.
  - `ReliabilityTestMetrics.java`: Measures reliability through testing.
  - `SoftwareSize.java`: Calculates lines of code and function points.
  - `TestMetricsAnalyzer.java`: Analyzes test-related metrics.

- `METRICS.md`: Detailed documentation on measurement theory and GQM framework.
- `README.md`: This file.

##  How the System Works

1. The program generates a list of synthetic transactions with:
   - Transaction ID,
   - Amount in UGX,
   - Location (Local or Foreign).

2. For each transaction set, the system runs:
   -SimpleRuleAlgorithm: flags transactions that are both **large (above a threshold)** and **Foreign**.
   -StatisticalAlgorithm: computes the **average amount** and **standard deviation** of all transactions, then flags transactions that are significantly above the average (z‑score based).

3. An Experiment class:
   - Runs both algorithms multiple times on the same transaction data.
   - Measures how long each one takes (in milliseconds).
   - Counts how many transactions are flagged as fraud.

4. A ConsoleReporter prints the results, typically showing:
   - The algorithm name,
   - Execution time,
   - Number of fraud cases detected,
   - A comparison between strategies.

This setup allows us to see how the two fraud‑detection strategies behave in terms of speed and sensitivity.
## Software Metrics Analysis

The project includes a comprehensive metrics dashboard that analyzes the codebase using various software metrics:

### Available Metrics
1. **Software Size Metrics**: Calculates Lines of Code (LOC) and Function Points (FP).
2. **Complexity Analysis**: Measures cyclomatic complexity of Java classes.
3. **Test Metrics**: Analyzes test coverage and effectiveness.
4. **Reliability Metrics**: Assesses system reliability through testing.
5. **Cost Metrics**: Estimates development and maintenance costs.

### Metrics Dashboard
The `RunMetrics.java` provides an interactive menu to run different analyses:
- Select from various metric calculations
- View results in the console
- Analyze the fraud detection system itself

This demonstrates practical application of software measurement theory to real code.

For detailed information on the measurement theory, GQM framework, and metrics implementation, see [METRICS.md](METRICS.md).
##  How to Compile and Run

From the **project root** folder (`Fraud-Detection-System`):

```bash
mkdir out
javac -d out */*.java */*/*.java
java -cp out main.Main
```

### Running the Metrics Dashboard
```bash
java -cp out main.RunMetrics
```

This will launch an interactive menu where you can select different software metrics analyses to run on the codebase.
