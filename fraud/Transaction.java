package fraud;

public class Transaction {
    private String id;
    private double amount;
    private String location;
    private double riskScore = 0.0;

    public Transaction(String id, double amount, String location) {
        this(id, amount, location, 0.0);
    }

    public Transaction(String id, double amount, String location, double riskScore) {
        this.id = id;
        this.amount = amount;
        this.location = location;
        this.riskScore = riskScore;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }
}
