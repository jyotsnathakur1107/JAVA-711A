package com.bank;

import com.bank.accounts.SavingsAccount;
import com.bank.customers.Customer;
import com.bank.loans.Loan;
import com.bank.db.DatabaseSetup;
import com.bank.db.CustomerDAO;
import com.bank.db.AccountDAO;
import com.bank.db.TransactionDAO;
import com.bank.db.LoanDAO;
import com.bank.db.DBConnection;

import static com.bank.util.BankUtil.generateAccountNumber;
import static com.bank.util.BankUtil.validateMinimumBalance;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankApplication {
    public static void main(String[] args) {
        DatabaseSetup.createTables();

        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        LoanDAO loanDAO = new LoanDAO();

        Map<String, Customer> customers = new HashMap<>();
        Map<String, SavingsAccount> accounts = new HashMap<>();

        System.out.println("=== Welcome to the Interactive Banking Management System ===");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add New Customer");
            System.out.println("2. Create New Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Apply for a Loan");
            System.out.println("6. Delete Customer Profile");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter a unique Customer ID (e.g. C002): ");
                String cid = scanner.nextLine();
                System.out.print("Enter Customer Name: ");
                String name = scanner.nextLine();

                Customer c = new Customer(cid, name);
                customers.put(cid, c);
                customerDAO.insertCustomer(cid, name);

            } else if (choice == 2) {
                System.out.print("Enter your existing Customer ID: ");
                String cid = scanner.nextLine();
                if (!customers.containsKey(cid)) {
                    System.out.println("Error: Customer not found in the current session. Please add the customer in this session first.");
                    continue;
                }

                System.out.print("Enter Initial Account Balance: ");
                double balance = 0;
                try {
                    balance = Double.parseDouble(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount.");
                    continue;
                }

                String accNum = generateAccountNumber();
                SavingsAccount acc = new SavingsAccount(accNum, balance, 5.0);
                customers.get(cid).linkAccount(acc);
                accounts.put(accNum, acc);

                accountDAO.insertAccount(accNum, cid, balance, "SAVINGS", 5.0);
                System.out.println("Account created successfully! ** Keep your Account Number Safe: " + accNum + " **");

            } else if (choice == 3) {
                System.out.print("Enter your Account Number: ");
                String accNum = scanner.nextLine();
                if (!accounts.containsKey(accNum)) {
                    System.out.println("Error: Account not found in the current session.");
                    continue;
                }

                System.out.print("Enter Deposit Amount: ");
                double amount = 0;
                try {
                    amount = Double.parseDouble(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount.");
                    continue;
                }

                SavingsAccount acc = accounts.get(accNum);
                acc.deposit(amount);
                accountDAO.updateBalance(accNum, acc.getBalance());
                transactionDAO.insertTransaction(accNum, "DEPOSIT", amount, acc.getBalance());

            } else if (choice == 4) {
                System.out.print("Enter your Account Number: ");
                String accNum = scanner.nextLine();
                if (!accounts.containsKey(accNum)) {
                    System.out.println("Error: Account not found in the current session.");
                    continue;
                }

                System.out.print("Enter Withdrawal Amount: ");
                double amount = 0;
                try {
                    amount = Double.parseDouble(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount.");
                    continue;
                }

                SavingsAccount acc = accounts.get(accNum);
                try {
                    validateMinimumBalance(acc.getBalance() - amount);
                    acc.withdraw(amount);
                    accountDAO.updateBalance(accNum, acc.getBalance());
                    transactionDAO.insertTransaction(accNum, "WITHDRAWAL", amount, acc.getBalance());
                } catch (Exception e) {
                    System.out.println("Transaction Failed: " + e.getMessage());
                }

            } else if (choice == 5) {
                System.out.print("Enter your Customer ID: ");
                String cid = scanner.nextLine();

                System.out.print("Enter Loan Amount: ");
                double loanAmount = 0;
                int tenure = 0;
                try {
                    loanAmount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Loan Tenure (in months): ");
                    tenure = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                    continue;
                }

                Loan loan = new Loan(loanAmount, 8.5, tenure);
                loan.calculateEMI();

                loanDAO.insertLoan(cid, loanAmount, 8.5, tenure, loan.calculateEMIValue());

            } else if (choice == 6) {
                System.out.print("Enter Customer ID to DELETE: ");
                String cid = scanner.nextLine();
                
                // Removing the customer deletes all their info via MySQL ON DELETE CASCADE!
                customerDAO.deleteCustomer(cid);
                
                // Also clean it out of our active session cache
                customers.remove(cid);
                
                System.out.println("Customer Profile and all associated banking data deleted!");

            } else if (choice == 7) {
                System.out.println("Saving final data & shutting down the Banking System. Goodbye!");
                DBConnection.closeConnection();
                scanner.close();
                break;
            } else {
                System.out.println("Invalid choice. Please select an option between 1 and 7.");
            }
        }
    }
}
