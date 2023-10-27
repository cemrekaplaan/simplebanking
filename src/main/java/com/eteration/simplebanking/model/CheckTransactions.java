package com.eteration.simplebanking.model;

public class CheckTransactions extends Transaction {
    private Account account;
    public CheckTransactions(double amount, Account account){
        super(amount, account);
        this.account = account;
    }
    @Override
    public void process() throws InsufficientBalanceException{
        account.post(this); // Added with the post() method.
        account.withdraw(getAmount());
    }
}
