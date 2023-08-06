public class IncomeExpense {
    private String description;
    private double amount;
    private Category category;

    public IncomeExpense(String description, double amount, Category category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }
}
