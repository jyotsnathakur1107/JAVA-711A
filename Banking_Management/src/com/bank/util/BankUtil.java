package com.bank.util;
import com.bank.exceptions.InsufficientBalanceException;
public class BankUtil {
    public static String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }
    public static void validateMinimumBalance(double balance) throws InsufficientBalanceException {
        if (balance < 1000) {
            throw new InsufficientBalanceException("Balance is below minimum limit");
        }
    }
}
