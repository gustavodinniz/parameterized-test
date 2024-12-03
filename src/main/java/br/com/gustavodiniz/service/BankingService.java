package br.com.gustavodiniz.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BankingService {

    private final Map<String, BigDecimal> accountBalances = new HashMap<>();

    public BankingService(){
        accountBalances.put("123456", new BigDecimal("1500")); //checking account
        accountBalances.put("654321", new BigDecimal("500"));  //savings account
    }

    public boolean isSufficientBalance(String accountNumber, BigDecimal amount){
        return accountBalances.getOrDefault(accountNumber, BigDecimal.ZERO).compareTo(amount) >= 0;
    }

    public boolean isValidAccountNumber(String accountNumber){
        return accountNumber != null && accountNumber.matches("\\d{6}");
    }

    public boolean transfer(String fromAccount, String toAccount, BigDecimal amount) {
        if (isSufficientBalance(fromAccount, amount)) {
            accountBalances.put(fromAccount, accountBalances.get(fromAccount).subtract(amount));
            accountBalances.put(toAccount, accountBalances.getOrDefault(toAccount, BigDecimal.ZERO).add(amount));
            return true;
        }
        return false;
    }

    public boolean isValidAccountType(String accountNumber) {
        // Let's consider that accounts with odd numbers are current accounts, and even accounts are savings accounts.
        return Integer.parseInt(accountNumber.substring(5)) % 2 == 0;
    }
}
