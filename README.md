# Expense Tracker (Java + MySQL)

## 📌 Project Overview

Expense Tracker is a console-based Java application that helps users manage and track their daily expenses. The application provides user authentication, expense management, spending analysis, budgeting, reporting, and CSV export functionality using a MySQL database.

This project follows a layered architecture using DAO (Data Access Object), Service, Model, and Utility classes to improve maintainability and scalability.

---

## 🚀 Features

### User Management

* User Registration
* User Login
* SHA-256 Password Hashing

### Expense Management

* Add Expense
* View Expenses
* Update Expense
* Delete Expense
* Search Expense

### Analytics & Reporting

* Total Spending Summary
* Category-wise Spending Analysis
* Budget Tracking
* Dashboard Summary
* Monthly Expense Report

### Export Functionality

* Export Expense Reports to CSV

---

## 🛠 Technologies Used

* Java
* JDBC
* MySQL
* IntelliJ IDEA
* Git
* GitHub

---

## 📂 Project Structure

```text
src
│
├── dao
│   ├── ExpenseDAO.java
│   └── UserDAO.java
│
├── model
│   ├── Expense.java
│   └── DashboardSummary.java
│
├── service
│   ├── ExpenseService.java
│   ├── ReportService.java
│   └── UserService.java
│
├── util
│   ├── DBConnection.java
│   └── PasswordUtil.java
│
└── Main.java
```

---

## 🗄 Database Schema

### users

| Column   | Type    |
| -------- | ------- |
| id       | INT     |
| username | VARCHAR |
| password | VARCHAR |

### expenses

| Column       | Type    |
| ------------ | ------- |
| id           | INT     |
| title        | VARCHAR |
| amount       | DOUBLE  |
| category     | VARCHAR |
| date         | VARCHAR |
| expense_date | DATE    |
| user_id      | INT     |

---

## ▶ How to Run

1. Clone the repository:

```bash
git clone https://github.com/Summu12322/Expense-Tracker-Java-MySQL.git
```

2. Create the MySQL database and required tables.

3. Update database credentials inside:

```java
DBConnection.java
```

4. Open the project in IntelliJ IDEA.

5. Run:

```java
Main.java
```

6. Register a new user and start tracking expenses.

---

## 🔐 Security

* Passwords are hashed using SHA-256 before being stored in the database.
* User-specific expense isolation is implemented using `user_id`.

---

## 📈 Future Enhancements

* JavaFX GUI
* Spring Boot REST API
* PDF Report Export
* Expense Charts and Graphs
* Email Notifications
* Expense Categories Dashboard

---

## 👨‍💻 Author

**Sumant Singh Mankotia**

Computer Science Engineer

GitHub: https://github.com/Summu12322
