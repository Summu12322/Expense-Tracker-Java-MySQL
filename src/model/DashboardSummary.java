package model;

public class DashboardSummary {

    private int totalCount;
    private double totalAmount;
    private double highestExpense;
    private double averageExpense;

    public DashboardSummary(
            int totalCount,
            double totalAmount,
            double highestExpense,
            double averageExpense) {

        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
        this.highestExpense = highestExpense;
        this.averageExpense = averageExpense;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getHighestExpense() {
        return highestExpense;
    }

    public double getAverageExpense() {
        return averageExpense;
    }
}
