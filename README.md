# Fraud Detection System - Software Metrics Project

## Course: SWE 2204 - Software Metrics
## Instructor: Dr. Richard Kimera

**Current Date:** March 1, 2026

---

## 👥 GROUP 3 MEMBERS

| # | Name | Student ID |
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

## 📅 PROJECT PROGRESS (As of March 1, 2026)

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
**Algorithm A: WHILE Loop**
while(i < transactions.size()) {
Transaction t = transactions.get(i);
comparisons++;
if(t.amount > threshold && t.location.equals("Foreign")) {
 fraud.add(t);
}
 i++;
}


