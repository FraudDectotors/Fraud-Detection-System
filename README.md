# Fraud Detection System - Software Metrics Project

## Course: SWE 2204 - Software Metrics
## Instructor: Dr. Richard Kimera

---

## 👥 GROUP MEMBERS

| Name          | RegNo             | Role      |
|---------------|-------------------|-----------|
| [Your Name]   |                   | Team Lead |
| MUTASIMU ALI  | 2024/BSE/121/PS   | Developer |
| [Member 3]    | [ID]              | Developer |
| [Member 4]    | [ID]              | Tester    |

---

## 📅 CURRENT WEEK: Week 4 & Week 5

| Week | Topic | Status |
|------|-------|--------|
| Week 4 | Goal-Question-Metric (GQM) | ✅ Done |
| Week 5 | Empirical Investigation | ✅ Done |
| Week 5b | Software Size | ⏳ Next |

---

## 🎯 WEEK 4: GOAL-QUESTION-METRIC

### Goal
> Detect fraud in less than 100 milliseconds

### Questions
1. Which algorithm is faster?
2. How many transactions per second?
3. Does speed drop with more data?

### Metrics
1. Execution time (milliseconds)
2. Transactions per second
3. Speed comparison between algorithms


## 🔬 WEEK 5: EMPIRICAL INVESTIGATION

### What We Did
We tested TWO algorithms to see which is faster:

**Algorithm 1: FOR Loop**
```java
for(int i = 0; i < transactions.size(); i++) {
    // check each transaction
}
int i = 0;
while(i < transactions.size()) {
    // check each transaction
    i++;
}
