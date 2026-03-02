# Fraud Detection System - Software Metrics Project

## Course: SWE 2204 - Software Metrics
## Instructor: Dr. Richard Kimera

---

## 👥 GROUP 3 MEMBERS

| # | Name | RegNo |
|---|------|------------|
| 1 | SENTONGO JOSEPH MARK | 2024/BSE/175/PS |
| 2 | MUTASIMU ALI | 2024/BSE/121/PS |
| 3 | KATO HAMUZAH KIZITO | 2024/BSE/079/PS |
| 4 | IMAMUT JULIANA | 2024/BSE/066/PS | 
| 5 | AINEMBABAZI FELIX | 2024/BSE/009/PS | 
| 6 | NALUKWAGO HADIJAH | 2024/BSE/138/PS |
| 7 | MUHUMUZA ISAIAH | 2024/BSE/110/PS | 
| 8 | AKANTORANA TRUST | 2023/BSE/159/PS |

---
## 📏 MEASUREMENT THEORY FOUNDATION

### What is Measurement Theory?
Measurement theory provides the scientific basis for how we measure things. It ensures our metrics are meaningful, valid, and reliable.

---

### 1. ENTITY BEING MEASURED

**Entity:** Fraud Detection Algorithm Execution

This refers to the actual process of running our Java code to scan banking transactions and identify potential fraud. The entity is the software system itself.

---

### 2. ATTRIBUTES MEASURED

| Attribute | What It Measures | Why It Matters |
|-----------|------------------|----------------|
| **Execution Time** | How fast the algorithm runs | Determines if we can detect fraud in real-time |
| **Throughput** | Transactions processed per second | Shows system capacity during peak banking hours |
| **Code Size** | Lines of code (LOC) | Indicates system complexity and development effort |
| **Comparisons** | Number of checks performed | Shows algorithm efficiency |

---

### 3. MEASUREMENT SCALES (The Most Important Part!)

In measurement theory, there are 4 scales. Here's how they apply to OUR system:

#### 📊 NOMINAL SCALE
**Definition:** Categories with no order or rank
**In Our System:**
- Algorithm type: FOR loop or WHILE loop
- Transaction location: "Local" or "Foreign"
- User type: "new" or "old"

#### 📊 ORDINAL SCALE
**Definition:** Categories with order but unequal gaps
**In Our System:**
- Risk level: Low < Medium < High
- Priority: Normal < Urgent < Critical

#### 📊 INTERVAL SCALE
**Definition:** Equal intervals, no true zero
**In Our System:**
- Time of day (hour): 1am to 2am = same as 2am to 3am
- Date of transaction

#### 📊 RATIO SCALE ⭐ (Most Important for Us!)
**Definition:** Has true zero, equal intervals
**In Our System:**
- **✅ Execution Time (milliseconds)** - 0ms means NO time
- **✅ Transactions Per Second** - 0 TPS means NO throughput
- **✅ Lines of Code** - 0 LOC means NO code
- **✅ Number of Comparisons** - 0 means NO comparisons
- **✅ Fraud Cases Found** - 0 means NO fraud detected

**Why Ratio Scale Matters:** Because we have a true zero, we can say "Algorithm A is twice as fast as Algorithm B" (50ms vs 25ms).

---

### 4. VALIDITY OF MEASUREMENT

Validity asks: "Are we measuring what we think we're measuring?"

#### ✅ FACE VALIDITY
**Question:** Does it look right?
**Our Evidence:** Execution time in milliseconds directly shows speed. If one algorithm takes 3.5ms and another takes 3.8ms, the faster one is obvious.

#### ✅ CONTENT VALIDITY
**Question:** Does it cover everything?
**Our Evidence:** We test ALL 10,000 transactions, not just a sample. Every transaction contributes to the measurement.

#### ✅ CONSTRUCT VALIDITY
**Question:** Does it measure the right concept?
**Our Evidence:** Time measures speed, comparisons measure efficiency, fraud count measures accuracy. Each metric measures ONE thing clearly.

#### ✅ CRITERION VALIDITY
**Question:** Does it match real-world expectations?
**Our Evidence:** Our measured times (3-4ms for 10,000 transactions) match typical Java performance benchmarks.

---

### 5. RELIABILITY OF MEASUREMENT

Reliability asks: "Are our measurements consistent?"

#### Test-Retest Reliability
We run the SAME algorithm on the SAME data 10 times:

## 📅 PROJECT PROGRESS

| Week | Topic | Status | Due Date |
|------|-------|--------|----------|
| Week 4 | Goal-Question-Metric (GQM) | ✅ Completed | Feb 2026 |
| Week 5 | Empirical Investigation | ✅ Completed | Feb 2026 |
| Week 6 | Software Size | 🔄 In Progress | March 2026 |
| Week 7 | Structural Complexity | ⏳ Upcoming | March 2026 |

---

## 🎯 WEEK 4: GOAL-QUESTION-METRIC (Completed)

### Goal
> Detect fraudulent transactions in less than 100 milliseconds to prevent real-time financial losses in the banking sector.

### Questions We Asked
| Question | Why It Matters |
|----------|----------------|
| **Q1:** Which algorithm detects fraud faster? (FOR vs WHILE loop) | Banks process millions of transactions daily |
| **Q2:** How many transactions can we process per second? | Must handle peak banking hours (10,000+ TPS) |
| **Q3:** Does performance drop when data size increases? | System must scale with bank growth |

### Metrics We Defined
| Metric | Unit | Target |
|--------|------|--------|
| **M1:** Execution time | milliseconds | <100ms |
| **M2:** Transactions per second | TPS | >10,000 |
| **M3:** Speedup factor | ratio | >1.0 |

---

## 🔬 WEEK 5: EMPIRICAL INVESTIGATION (Completed - Feb 2026)

### What We Did
We tested two algorithms on the **same data** to see which performs better for fraud detection:

**Algorithm A: FOR Loop**
```java
for(int i = 0; i < transactions.size(); i++) {
 Transaction t = transactions.get(i);
comparisons++;
 if(t.amount > threshold && t.location.equals("Foreign")) {
fraud.add(t);
 }
}
```
**Algorithm B: WHILE Loop**
```java
while(i < transactions.size()) {
Transaction t = transactions.get(i);
comparisons++;
if(t.amount > threshold && t.location.equals("Foreign")) {
 fraud.add(t);
}
 i++;
}
```


