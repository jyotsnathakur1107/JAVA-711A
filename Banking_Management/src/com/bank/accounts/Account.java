package com.bank.accounts;
import com.bank.exceptions.InsufficientBalanceException;
public class Account {
    protected String accountNumber;
    protected double balance;
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > this.balance) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        if (this.balance - amount < 1000) {
            throw new InsufficientBalanceException("Minimum balance of 1000 must be maintained");
        }
        this.balance -= amount;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }
}
