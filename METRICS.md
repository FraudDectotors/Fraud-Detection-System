IMPLEMENTATIONS OF SOFTWARE METRICS

MEASUREMENT THEORY

Measurement theory ensures that the metrics we collect are well defined and meaningful. In this project, measurements are obtained when each fraud detection algorithm analyzes the same set of `Transaction` data and returns a `DetectionResult`.

Entities being measured.
 Execution time. (`DetectionResult.timeMs`)
 Number of suspicious/fraud transactions flagged (`DetectionResult.fraudFound`)
 The list of flagged transactions (`DetectionResult.flagged`)

Measurement Scale Type.
In this Fraud detection system, we used the nominal and ratio scale.
Entity| Scale type| Justification
------:|:---------:|:-----------
Algorithm name| Nominal|It is label used to classify the algorithm but no arithmetic can be done on it.
Execution time| Ratio| Measured in milliseconds , has a true zero and ratios are meaningful. 
Number of fraud transactions flagged| Ratio| It is a count. 
