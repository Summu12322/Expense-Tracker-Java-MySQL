package dao;
import model.Expense;
import model.DashboardSummary;
import util.DBConnection;

import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    public void addExpense(
            String title,
            double amount,
            String category,
            String date,
            Date expenseDate,
            int userId) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO expenses " + "(title,amount,category,date,expense_date,user_id) " + "VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, title);

            ps.setDouble(2, amount);

            ps.setString(3, category);

            ps.setString(4, date);

            ps.setDate(5, expenseDate);

            ps.setInt(6, userId);

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
    public boolean updateExpense(
            String title,
            double amount,
            String category,
            String date,
            java.sql.Date sqlDate,
            int updateId,
            int loggedInUserId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "UPDATE expenses " +
                            "SET title=?, amount=?, category=?, date=?, expense_date=? " +
                            "WHERE id=? AND user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, title);
            ps.setDouble(2, amount);
            ps.setString(3, category);
            ps.setString(4, date);
            ps.setDate(5, sqlDate);
            ps.setInt(6, updateId);
            ps.setInt(7, loggedInUserId);

            int rowsAffected =
                    ps.executeUpdate();

            return rowsAffected > 0;

        } catch(Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    public ResultSet findExpenseById(
            int expenseId,
            int userId) throws Exception {

        Connection con =
                DBConnection.getConnection();

        String query =
                "SELECT * FROM expenses WHERE id=? AND user_id=?";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(1, expenseId);
        ps.setInt(2, userId);

        return ps.executeQuery();
    }

    public ResultSet searchExpense(
            String keyword,
            int userId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM expenses " +
                            "WHERE (title LIKE ? OR category LIKE ?) " +
                            "AND user_id=? " +
                            "ORDER BY id DESC";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setInt(3, userId);

            return ps.executeQuery();

        } catch(Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public double getTotalSpending(
            int userId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT SUM(amount) AS total " +
                            "FROM expenses WHERE user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble("total");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    public ResultSet getCategoryWiseSpending(
            int userId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT category, SUM(amount) AS total " +
                            "FROM expenses " +
                            "WHERE user_id=? " +
                            "GROUP BY category " +
                            "ORDER BY total DESC";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);

            return ps.executeQuery();

        } catch(Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public DashboardSummary getDashboardSummary(
            int userId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT COUNT(*) AS totalCount, " +
                            "SUM(amount) AS totalAmount, " +
                            "MAX(amount) AS highestExpense, " +
                            "AVG(amount) AS averageExpense " +
                            "FROM expenses WHERE user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return new DashboardSummary(
                        rs.getInt("totalCount"),
                        rs.getDouble("totalAmount"),
                        rs.getDouble("highestExpense"),
                        rs.getDouble("averageExpense"));
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public List<Expense> getMonthlyReport(
            String monthYear,
            int userId) {

        List<Expense> expenses =
                new ArrayList<>();

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM expenses " +
                            "WHERE user_id=? " +
                            "AND DATE_FORMAT(expense_date,'%m/%Y')=? " +
                            "ORDER BY expense_date DESC";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setString(2, monthYear);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                expenses.add(
                        new Expense(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getDouble("amount"),
                                rs.getString("category"),
                                rs.getString("date")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return expenses;
    }

    public java.util.List<Expense> getAllExpenses(
            int userId) {

        java.util.List<Expense> expenses =
                new java.util.ArrayList<>();

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM expenses " +
                            "WHERE user_id=? " +
                            "ORDER BY expense_date DESC";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                expenses.add(
                        new Expense(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getDouble("amount"),
                                rs.getString("category"),
                                rs.getString("date")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return expenses;
    }

}
