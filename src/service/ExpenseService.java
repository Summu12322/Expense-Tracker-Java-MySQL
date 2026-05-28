package service;

import dao.ExpenseDAO;
import model.Expense;
import model.DashboardSummary;

import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;

import model.DashboardSummary;
import dao.ExpenseDAO;
import util.DBConnection;

import java.sql.*;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ExpenseService {
    ExpenseDAO expenseDAO =
            new ExpenseDAO();
    public void addExpense(
            Scanner sc,
            int loggedInUserId) {

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        double amount;

        System.out.print("Enter Amount: ");

        if(sc.hasNextDouble()) {

            amount = sc.nextDouble();
            sc.nextLine();

        } else {

            System.out.println(
                    "Invalid Amount! Numbers only.");

            sc.nextLine();
            return;
        }

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print(
                "Enter Date (dd/MM/yyyy): ");

        String date = sc.nextLine();

        try {

            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter
                            .ofPattern("dd/MM/yyyy");

            java.time.LocalDate localDate =
                    java.time.LocalDate.parse(
                            date,
                            formatter);

            java.sql.Date sqlDate =
                    java.sql.Date.valueOf(localDate);

            expenseDAO.addExpense(
                    title,
                    amount,
                    category,
                    date,
                    sqlDate,
                    loggedInUserId);

            System.out.println(
                    "Expense Added Successfully!");

        } catch(Exception ex) {

            System.out.println(
                    "Invalid Date! Use dd/MM/yyyy");

            ex.printStackTrace();
        }
    }
    public void viewExpenses(
            int loggedInUserId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM expenses " +
                            "WHERE user_id=? " +
                            "ORDER BY expense_date DESC";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, loggedInUserId);

            ResultSet rs =
                    ps.executeQuery();

            System.out.println(
                    "\n--- Expense List ---");

            boolean found = false;

            while(rs.next()) {

                found = true;

                System.out.println();

                System.out.println(
                        "ID: "
                                + rs.getInt("id"));

                System.out.println(
                        "Title: "
                                + rs.getString("title"));

                System.out.printf(
                        "Amount: ₹%.2f\n",
                        rs.getDouble("amount"));

                System.out.println(
                        "Category: "
                                + rs.getString("category"));

                System.out.println(
                        "Date: "
                                + rs.getString("date"));

                System.out.println(
                        "-------------------");
            }

            if(!found) {

                System.out.println(
                        "No Expenses Found.");
            }

        } catch(Exception ex) {

            ex.printStackTrace();
        }
    }
    public void deleteExpense(
            Scanner sc,
            int loggedInUserId) {

        System.out.print(
                "Enter Expense ID to Delete: ");

        if(!sc.hasNextInt()) {

            System.out.println(
                    "Invalid ID! Numbers only.");

            sc.nextLine();
            return;
        }

        int deleteId =
                sc.nextInt();

        sc.nextLine();

        try {

            Connection con =
                    DBConnection.getConnection();

            String searchQuery =
                    "SELECT * FROM expenses " +
                            "WHERE id=? AND user_id=?";

            PreparedStatement searchPs =
                    con.prepareStatement(
                            searchQuery);

            searchPs.setInt(1, deleteId);
            searchPs.setInt(2, loggedInUserId);

            ResultSet rs =
                    searchPs.executeQuery();

            if(rs.next()) {

                System.out.println(
                        "\nExpense Found:");

                System.out.println(
                        "Title: "
                                + rs.getString("title"));

                System.out.println(
                        "Amount: ₹"
                                + rs.getDouble("amount"));

                System.out.println(
                        "Category: "
                                + rs.getString("category"));

                System.out.println(
                        "Date: "
                                + rs.getString("date"));

                System.out.print(
                        "\nAre you sure? (yes/no): ");

                String confirm =
                        sc.nextLine();

                if(confirm.equalsIgnoreCase("yes")) {

                    String deleteQuery =
                            "DELETE FROM expenses " +
                                    "WHERE id=? AND user_id=?";

                    PreparedStatement deletePs =
                            con.prepareStatement(
                                    deleteQuery);

                    deletePs.setInt(1, deleteId);
                    deletePs.setInt(2, loggedInUserId);

                    int rows =
                            deletePs.executeUpdate();

                    if(rows > 0) {

                        System.out.println(
                                "Expense Deleted Successfully!");
                    }

                } else {

                    System.out.println(
                            "Delete Cancelled.");
                }

            } else {

                System.out.println(
                        "Expense Not Found.");
            }

        } catch(Exception ex) {

            ex.printStackTrace();
        }
    }

    public void updateExpense(
            Scanner sc,
            int loggedInUserId) {

        System.out.print(
                "Enter Expense ID to Update: ");

        if(!sc.hasNextInt()) {

            System.out.println(
                    "Invalid ID! Numbers only.");

            sc.nextLine();
            return;
        }

        int updateId =
                sc.nextInt();

        sc.nextLine();

        try {

            ResultSet rs =
                    expenseDAO.findExpenseById(
                            updateId,
                            loggedInUserId);

            if(rs.next()) {

                System.out.println(
                        "\nCurrent Expense Details:");

                System.out.println(
                        "Title: "
                                + rs.getString("title"));

                System.out.println(
                        "Amount: "
                                + rs.getDouble("amount"));

                System.out.println(
                        "Category: "
                                + rs.getString("category"));

                System.out.println(
                        "Date: "
                                + rs.getString("date"));

                System.out.print(
                        "\nEnter New Title: ");

                String newTitle =
                        sc.nextLine();

                System.out.print(
                        "Enter New Amount: ");

                if(!sc.hasNextDouble()) {

                    System.out.println(
                            "Invalid Amount!");

                    sc.nextLine();
                    return;
                }

                double newAmount =
                        sc.nextDouble();

                sc.nextLine();

                System.out.print(
                        "Enter New Category: ");

                String newCategory =
                        sc.nextLine();

                System.out.print(
                        "Enter New Date (dd/MM/yyyy): ");

                String newDate =
                        sc.nextLine();

                try {

                    java.time.format.DateTimeFormatter formatter =
                            java.time.format.DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy");

                    java.time.LocalDate localDate =
                            java.time.LocalDate.parse(
                                    newDate,
                                    formatter);

                    java.sql.Date sqlDate =
                            java.sql.Date.valueOf(localDate);

                    boolean updated =
                            expenseDAO.updateExpense(
                                    newTitle,
                                    newAmount,
                                    newCategory,
                                    newDate,
                                    sqlDate,
                                    updateId,
                                    loggedInUserId);

                    if(updated) {

                        System.out.println(
                                "Expense Updated Successfully!");

                    } else {

                        System.out.println(
                                "Expense Update Failed.");
                    }
                } catch(Exception e) {

                    System.out.println(
                            "Invalid Date! Use dd/MM/yyyy");
                }

            } else {

                System.out.println(
                        "Expense Not Found.");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public void searchExpense(
            Scanner sc,
            int loggedInUserId) {

        System.out.print(
                "Enter Title or Category to Search: ");

        String keyword =
                sc.nextLine();

        if(keyword.trim().isEmpty()) {

            System.out.println(
                    "Search cannot be empty.");

            return;
        }

        try {

            ResultSet rs =
                    expenseDAO.searchExpense(
                            keyword,
                            loggedInUserId);

            boolean found = false;

            System.out.println(
                    "\n--- Search Results ---");

            while(rs.next()) {

                found = true;

                System.out.println();
                System.out.println(
                        "ID: "
                                + rs.getInt("id"));

                System.out.println(
                        "Title: "
                                + rs.getString("title"));

                System.out.printf(
                        "Amount: ₹%.2f\n",
                        rs.getDouble("amount"));

                System.out.println(
                        "Category: "
                                + rs.getString("category"));

                System.out.println(
                        "Date: "
                                + rs.getString("date"));

                System.out.println(
                        "-------------------");
            }

            if(!found) {

                System.out.println(
                        "No Matching Expense Found.");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }
    }


    public void totalSpending(
            int loggedInUserId) {

        double total =
                expenseDAO.getTotalSpending(
                        loggedInUserId);

        System.out.println(
                "\n===== Spending Summary =====");

        if(total == 0) {

            System.out.println(
                    "No Expenses Found.");

        } else {

            System.out.printf(
                    "Total Expenses: ₹%.2f\n",
                    total);
        }
    }

    public void categoryWiseSpending(
            int loggedInUserId) {

        try {

            ResultSet rs =
                    expenseDAO.getCategoryWiseSpending(
                            loggedInUserId);

            System.out.println(
                    "\n===== Category-wise Spending =====");

            boolean found = false;

            while(rs.next()) {

                found = true;

                System.out.printf(
                        "%-15s ₹%.2f\n",
                        rs.getString("category"),
                        rs.getDouble("total")
                );
            }

            if(!found) {

                System.out.println(
                        "No Expenses Found.");
            }

            System.out.println(
                    "--------------------------");

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public void budgetStatus(
            Scanner sc,
            int loggedInUserId) {

        System.out.print(
                "Enter Your Budget: ");

        if(!sc.hasNextDouble()) {

            System.out.println(
                    "Invalid Budget! Numbers only.");

            sc.nextLine();
            return;
        }

        double budget =
                sc.nextDouble();

        sc.nextLine();

        double totalSpending =
                expenseDAO.getTotalSpending(
                        loggedInUserId);

        double remaining =
                budget - totalSpending;

        double percentageUsed = 0;

        if(budget > 0) {

            percentageUsed =
                    (totalSpending / budget) * 100;
        }

        System.out.println(
                "\n===== Budget Status =====");

        System.out.printf(
                "Budget: ₹%.2f\n",
                budget);

        System.out.printf(
                "Spent : ₹%.2f\n",
                totalSpending);

        System.out.printf(
                "Left  : ₹%.2f\n",
                remaining);

        System.out.printf(
                "Used  : %.2f%%\n",
                percentageUsed);

        if(totalSpending > budget) {

            System.out.println(
                    "⚠ Budget Exceeded!");

        } else {

            System.out.println(
                    "✅ Budget Under Control");
        }
    }

    public void dashboardSummary(
            int loggedInUserId) {

        DashboardSummary summary =
                expenseDAO.getDashboardSummary(
                        loggedInUserId);

        if(summary == null) {

            System.out.println(
                    "No Expenses Found.");

            return;
        }

        System.out.println(
                "\n===== Dashboard Summary =====");

        System.out.println(
                "Total Expenses: "
                        + summary.getTotalCount());

        System.out.printf(
                "Total Spending: ₹%.2f\n",
                summary.getTotalAmount());

        System.out.printf(
                "Highest Model.Expense: ₹%.2f\n",
                summary.getHighestExpense());

        System.out.printf(
                "Average Model.Expense: ₹%.2f\n",
                summary.getAverageExpense());
    }

    public void monthlyReport(
            Scanner sc,
            int loggedInUserId) {

        System.out.print(
                "Enter Month and Year (MM/yyyy): ");

        String monthYear =
                sc.nextLine();

        if(!monthYear.matches("\\d{2}/\\d{4}")) {

            System.out.println(
                    "Invalid Format! Use MM/yyyy");

            return;
        }

        List<Expense> expenses =
                expenseDAO.getMonthlyReport(
                        monthYear,
                        loggedInUserId);

        System.out.println(
                "\n===== Monthly Report =====");

        if(expenses.isEmpty()) {

            System.out.println(
                    "No Expenses Found.");

            return;
        }

        double monthlyTotal = 0;

        for(Expense expense : expenses) {

            System.out.println();

            System.out.println(
                    "Title: "
                            + expense.getTitle());

            System.out.printf(
                    "Amount: ₹%.2f\n",
                    expense.getAmount());

            System.out.println(
                    "Category: "
                            + expense.getCategory());

            System.out.println(
                    "Date: "
                            + expense.getDate());

            System.out.println(
                    "-------------------");

            monthlyTotal +=
                    expense.getAmount();
        }

        System.out.printf(
                "\nMonthly Total: ₹%.2f\n",
                monthlyTotal);
    }

    public void exportReport(
            int loggedInUserId) {

        try {

            java.util.List<Expense> expenses =
                    expenseDAO.getAllExpenses(
                            loggedInUserId);

            if(expenses.isEmpty()) {

                System.out.println(
                        "No Expenses Found.");

                return;
            }

            String fileName =
                    "expenses_report_"
                            + System.currentTimeMillis()
                            + ".csv";

            OutputStreamWriter writer =
                    new OutputStreamWriter(
                            new FileOutputStream(fileName),
                            StandardCharsets.UTF_8
                    );

            writer.write("\uFEFF");

            writer.write(
                    "ID,Title,Amount,Category,Date\n");

            double total = 0;

            for(Expense expense : expenses) {

                writer.write(
                        expense.getId() + "," +
                                "\"" + expense.getTitle() + "\"," +
                                "\"₹" +
                                String.format(
                                        "%.2f",
                                        expense.getAmount()) +
                                "\"," +
                                "\"" +
                                expense.getCategory() +
                                "\"," +
                                "\"" +
                                expense.getDate() +
                                "\"" +
                                "\n"
                );

                total +=
                        expense.getAmount();
            }

            writer.write("\n");

            writer.write(
                    "TOTAL,,\"₹" +
                            String.format(
                                    "%.2f",
                                    total) +
                            "\""
            );

            writer.close();

            System.out.println(
                    "CSV Report Exported Successfully!");

            System.out.println(
                    "File Saved: " + fileName);

        } catch(Exception ex) {

            ex.printStackTrace();
        }
    }
}
