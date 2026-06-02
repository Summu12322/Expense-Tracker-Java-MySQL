CREATE DATABASE expense_tracker;

USE expense_tracker;

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE expenses (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          title VARCHAR(255) NOT NULL,
                          amount DOUBLE NOT NULL,
                          category VARCHAR(100),
                          date VARCHAR(20),
                          expense_date DATE,
                          user_id INT,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);