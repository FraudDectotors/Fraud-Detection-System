public class ProductAttribute {
    private String reliability;
    private String performance;
    private String usability;

    public ProductAttribute(String reliability, String performance, String usability) {
        this.reliability = reliability;
        this.performance = performance;
        this.usability = usability;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getUsability() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability = usability;
    }

    @Override
    public String toString() {
        return "ProductAttribute{" +
                "reliability='" + reliability + '\'' +
                ", performance='" + performance + '\'' +
                ", usability='" + usability + '\'' +
                '}';
    }
}