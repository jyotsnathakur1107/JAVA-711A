package com.bank.loans;
public class Loan {
    private double loanAmount;
    private double interestRate;
    private int tenure;
    public Loan(double loanAmount, double interestRate, int tenure) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.tenure = tenure;
    }
    public void calculateEMI() {
        double emi = calculateEMIValue();
        System.out.println("Calculated EMI: " + Math.round(emi * 100.0) / 100.0);
    }
    
    public double calculateEMIValue() {
        double r = (interestRate / 100) / 12;
        return (loanAmount * r * Math.pow(1 + r, tenure)) / (Math.pow(1 + r, tenure) - 1);
    }
}
