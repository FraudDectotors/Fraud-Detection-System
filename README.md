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

This project is a **fraud‑detection system written in Java** that we intedn to use for our coursework.
The main idea is to:

- Generate realistic bank‑style transactions (with ID, amount in UGX, and location).
- Apply different fraud‑detection strategies to detect suspicious transactions.
- Measure performance and behavior using metrics such as **execution time** and **number of fraud cases found**.

Instead of comparing two identical loops, our current version uses **two different fraud‑detection strategies**:
- A **rule‑based** algorithm that flags high‑amount foreign transactions.
- A **statistical** algorithm that flags transactions that are too far from the average amount (z‑score based).

##  Code Structure

The project is organized as a **multi‑file, modular Java project** with clear packages:

## Project Structure

- FraudDetectionSystem
  - out
    - compiled class files
  - src
    - fraud
      - Transaction.java
      - DetectionResult.java
      - algo
        - Algorithm.java
        - SimpleRuleAlgorithm.java
        - StatisticalAlgorithm.java
    - experiment
      - Experiment.java
      - ExperimentReporter.java
      - ConsoleReporter.java
    - metrics
      - Metric.java
    - main
      - Main.java
      - TransactionGenerator.java
  - metrics.md
    - weekly metrics documentation


text

- fraud: contains the transaction model and the fraud‑detection algorithms.
- experiment: runs multiple experiments, collects results, and prints them.
- metrics: defines the Metric<T> interface so we can plug in new metrics without changing the core logic.
- main: the entry point (Main) and tools for generating test data.

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

##  How to Compile and Run

From the **project root** folder (`FraudDetectionSystem`):

```bash
cd C:\FraudDetectionSystem
mkdir out
javac -d out src\**\*.java
java -cp out main.Main
