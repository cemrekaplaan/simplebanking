package com.eteration.simplebanking.model;

import java.time.LocalDateTime;

public class DepositTransaction extends Transaction {

    private Account account;

    public DepositTransaction(double amount, LocalDateTime now){
        super(amount, now);
    }

    @Override
    public String toString() {
      return "DepositTransaction{" + "date = " + getDate() + ", amount = " + getAmount() + '}';
    }

    @Override
    public void process() {
        account.credit(getAmount());
    }
}
