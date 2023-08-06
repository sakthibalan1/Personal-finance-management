import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FinanceTracker {
    private List<IncomeExpense> incomeList;
    private List<IncomeExpense> expenseList;
    private double savings;
    private Map<Category, Double> budget;
    private static final String FILE_NAME = "financial_data.txt";

    public FinanceTracker() {
        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        savings = 0.0;
        budget = new HashMap<>();
        budget.put(Category.INCOME, 0.0);
        budget.put(Category.EXPENSE, 0.0);
    }

    public void addIncome(String description, double amount, Category category) {
        incomeList.add(new IncomeExpense(description, amount, category));
        savings += amount;
        budget.put(Category.INCOME, budget.get(Category.INCOME) + amount);
    }

    public void addExpense(String description, double amount, Category category) {
        expenseList.add(new IncomeExpense(description, amount, category));
        savings -= amount;
        budget.put(Category.EXPENSE, budget.get(Category.EXPENSE) + amount);
    }

    public void showBalance() {
        System.out.println("Current Balance: " + savings);
    }

    public void showIncome() {
        System.out.println("Income List:");
        for (IncomeExpense income : incomeList) {
            System.out.println(income.getDescription() + ": " + income.getAmount());
        }
    }

    public void showExpenses() {
        System.out.println("Expense List:");
        for (IncomeExpense expense : expenseList) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public void showBudget() {
        System.out.println("Budget:");
        System.out.println("Income Budget: " + budget.get(Category.INCOME));
        System.out.println("Expense Budget: " + budget.get(Category.EXPENSE));
    }

    public void generateFinancialReport() {
        try {
            FileWriter reportWriter = new FileWriter("financial_report.txt");
            BufferedWriter writer = new BufferedWriter(reportWriter);

            writer.write("Financial Report\n");
            writer.write("---------------\n");
            writer.write("Income:\n");
            for (IncomeExpense income : incomeList) {
                writer.write(income.getDescription() + ": " + income.getAmount() + "\n");
            }

            writer.write("\nExpenses:\n");
            for (IncomeExpense expense : expenseList) {
                writer.write(expense.getDescription() + ": " + expense.getAmount() + "\n");
            }

            writer.write("\nBudget:\n");
            writer.write("Income Budget: " + budget.get(Category.INCOME) + "\n");
            writer.write("Expense Budget: " + budget.get(Category.EXPENSE) + "\n");

            writer.write("\nCurrent Balance: " + savings + "\n");
            writer.close();
            System.out.println("Financial report generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating financial report.");
            e.printStackTrace();
        }
    }

    public void saveDataToFile() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Save income data to file
            for (IncomeExpense income : incomeList) {
                writer.write("Income," + income.getDescription() + "," + income.getAmount() + "\n");
            }

            // Save expense data to file
            for (IncomeExpense expense : expenseList) {
                writer.write("Expense," + expense.getDescription() + "," + expense.getAmount() + "\n");
            }

            // Save savings and budget data to file
            writer.write("Savings," + savings + "\n");
            writer.write("IncomeBudget," + budget.get(Category.INCOME) + "\n");
            writer.write("ExpenseBudget," + budget.get(Category.EXPENSE) + "\n");

            writer.close();
            System.out.println("Data saved to file: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + FILE_NAME);
            e.printStackTrace();
        }
    }

    public void loadDataFromFile() {
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String type = data[0];
                    String description = data[1];
                    double amount = Double.parseDouble(data[2]);
                    Category category = type.equals("Income") ? Category.INCOME : Category.EXPENSE;

                    if (type.equals("Income")) {
                        addIncome(description, amount, category);
                    } else if (type.equals("Expense")) {
                        addExpense(description, amount, category);
                    }
                } else if (data.length == 2) {
                    String label = data[0];
                    double value = Double.parseDouble(data[1]);

                    if (label.equals("Savings")) {
                        savings = value;
                    } else if (label.equals("IncomeBudget")) {
                        budget.put(Category.INCOME, value);
                    } else if (label.equals("ExpenseBudget")) {
                        budget.put(Category.EXPENSE, value);
                    }
                }
            }

            reader.close();
            System.out.println("Data loaded from file: " + FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error reading data from file: " + FILE_NAME);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FinanceTracker tracker = new FinanceTracker();
        tracker.loadDataFromFile();
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("Personal Finance Tracker");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. Show Balance");
            System.out.println("4. Show Income");
            System.out.println("5. Show Expenses");
            System.out.println("6. Show Budget");
            System.out.println("7. Generate Financial Report");
            System.out.println("8. Save Data to File and Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the non-integer input to avoid an infinite loop
                continue; // Continue the loop to ask for a valid input again
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Income Description: ");
                    String incomeDescription = scanner.nextLine();
                    System.out.print("Enter Income Amount: ");
                    double incomeAmount = scanner.nextDouble();
                    scanner.nextLine();
                    tracker.addIncome(incomeDescription, incomeAmount, Category.INCOME);
                    break;
                case 2:
                    System.out.print("Enter Expense Description: ");
                    String expenseDescription = scanner.nextLine();
                    System.out.print("Enter Expense Amount: ");
                    double expenseAmount = scanner.nextDouble();
                    scanner.nextLine();
                    tracker.addExpense(expenseDescription, expenseAmount, Category.EXPENSE);
                    break;
                case 3:
                    tracker.showBalance();
                    break;
                case 4:
                    tracker.showIncome();
                    break;
                case 5:
                    tracker.showExpenses();
                    break;
                case 6:
                    tracker.showBudget();
                    break;
                case 7:
                    tracker.generateFinancialReport();
                    break;
                case 8:
                    tracker.saveDataToFile();
                    System.out.println("Exiting the Personal Finance Tracker...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
}
