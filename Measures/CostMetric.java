package Measures;

public class CostMetric {
    private double developmentCost;
    private double maintenanceCost;
    private double resourceCost;

    public CostMetric(double developmentCost, double maintenanceCost, double resourceCost) {
        this.developmentCost = developmentCost;
        this.maintenanceCost = maintenanceCost;
        this.resourceCost = resourceCost;
    }

    public double calculateTotalCost() {
        return developmentCost + maintenanceCost + resourceCost;
    }

    public double getDevelopmentCost() {
        return developmentCost;
    }

    public void setDevelopmentCost(double developmentCost) {
        this.developmentCost = developmentCost;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(double maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public double getResourceCost() {
        return resourceCost;
    }

    public void setResourceCost(double resourceCost) {
        this.resourceCost = resourceCost;
    }

    @Override
    public String toString() {
        return "CostMetric{" +
                "developmentCost=" + developmentCost +
                ", maintenanceCost=" + maintenanceCost +
                ", resourceCost=" + resourceCost +
                ", totalCost=" + calculateTotalCost() +
                '}';
    }
}