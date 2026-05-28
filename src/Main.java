import service.ExpenseService;
import service.UserService;
import util.DBConnection;

import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        DBConnection.getConnection();

        Scanner sc = new Scanner(System.in);
        int loggedInUserId = -1;
        int choice = 0;
        UserService userService =
                new UserService();
        ExpenseService expenseService =
                new ExpenseService();

        System.out.println("===== Welcome =====");
        System.out.println("1. Register");
        System.out.println("2. Login");

        int authChoice = sc.nextInt();
        sc.nextLine();


// ===== REGISTER =====
        if (authChoice == 1) {

            System.out.print("Enter Username: ");
            String username = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            boolean registered =
                    userService.register(
                            username,
                            password);

            if (registered) {

                System.out.println(
                        "Registration Successful!");

            } else {

                System.out.println(
                        "Username already exists! Please login or use another username.");

                return;
            }
        }


// ===== LOGIN =====
            System.out.println("===== Login =====");

            System.out.print("Enter Username: ");
            String loginUsername = sc.nextLine();

            System.out.print("Enter Password: ");
            String loginPassword = sc.nextLine();

            loggedInUserId =
                    userService.login(
                            loginUsername,
                            loginPassword);

            if(loggedInUserId != -1) {

                System.out.println(
                        "Login Successful!");

            } else {

                System.out.println(
                        "Invalid Username or Password");

                return;
            }


            do {

                System.out.println("\n===== Model.Expense Tracker =====");
                System.out.println("1. Add Model.Expense");
                System.out.println("2. View Expenses");
                System.out.println("3. Delete Model.Expense");
                System.out.println("4. Update Model.Expense");
                System.out.println("5. Search Model.Expense");
                System.out.println("6. Total Spending");
                System.out.println("7. Category-wise Spending");
                System.out.println("8. Budget Status");
                System.out.println("9. Dashboard Summary");
                System.out.println("10. Monthly Report");
                System.out.println("11. Export Report");
                System.out.println("12. Exit");

                System.out.print("Enter your choice: ");

                if (!sc.hasNextInt()) {

                    System.out.println("Invalid choice! Numbers only.");
                    sc.next();
                    continue;
                }

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:

                        expenseService.addExpense(
                                sc,
                                loggedInUserId);

                        break;

                    case 2:

                        expenseService.viewExpenses(
                                loggedInUserId);

                        break;

                    case 3:

                        expenseService.deleteExpense(
                                sc,
                                loggedInUserId);

                        break;

                    case 4:

                        expenseService.updateExpense(
                                sc,
                                loggedInUserId);

                        break;

                    case 5:

                        expenseService.searchExpense(
                                sc,
                                loggedInUserId);

                        break;

                    case 6:

                        expenseService.totalSpending(
                                loggedInUserId);

                        break;

                    case 7:

                        expenseService.categoryWiseSpending(
                                loggedInUserId);

                        break;

                    case 8:

                        expenseService.budgetStatus(
                                sc,
                                loggedInUserId);

                        break;

                    case 9:

                        expenseService.dashboardSummary(
                                loggedInUserId);

                        break;

                    case 10:

                        expenseService.monthlyReport(
                                sc,
                                loggedInUserId);

                        break;

                    case 11:

                        expenseService.exportReport(
                                loggedInUserId);

                        break;

                    case 12:
                        System.out.println("Exiting Program...");
                        break;

                    default:
                        System.out.println("Invalid Choice");

                }


            } while (choice != 12);

        }
    }
