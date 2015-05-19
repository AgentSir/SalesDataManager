public class SalesPerson {

    private String name;
    private int salesAmount;
    private double commission;

    public SalesPerson(String name, int salesAmount, double commission){
        this.name = name;
        this.salesAmount = salesAmount;
        this.commission = commission;

    }

    public String getName() {
        return name;
    }

    public int getSalesAmount() {
        return salesAmount;
    }

    public double getCommission() {
        return commission;
    }
}
