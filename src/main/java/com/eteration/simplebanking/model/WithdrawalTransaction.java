package com.eteration.simplebanking.model;

import java.time.LocalDateTime;

public class WithdrawalTransaction extends Transaction {
    private Account account;

    public WithdrawalTransaction(double amount, LocalDateTime now){
        super(amount, now);
    }
    @Override
    public void process() throws InsufficientBalanceException {
    account.debit();
    }
}


