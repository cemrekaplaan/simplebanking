package com.eteration.simplebanking.model;

import java.time.LocalDateTime;

public class BillPaymentTransaction extends Transaction {
    private String payee;
    private Account acc;

    public BillPaymentTransaction(String payee, LocalDateTime now, double amount) {
        super(amount, LocalDateTime.now());
        this.payee = payee;
    }

    public String getPayee() {
        return payee;
    }

    @Override
    public void process() throws InsufficientBalanceException {
        acc.debit();
    }
}
