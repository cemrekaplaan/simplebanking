package com.eteration.simplebanking.controller;

public class TransactionStatus {
    private String status; // True or False
    private String type; // Deposite, Withdrawal..
    private double amount; // Amount of Transaction
    private String payee; // Payee's name

    public TransactionStatus(String status, String type, double amount, String payee) {
        this.status = status;
        this.type = type;
        this.amount = amount;
        this.payee = payee;
    }

    public static void main(String[] args) {
        // Creating an object with the 'new' keyword.
        TransactionStatus depositStatus = new TransactionStatus("True", "Deposit", 1000.0, "Vodafone");

        depositStatus.setStatus("False");
        depositStatus.setType("Withdrawal");
        depositStatus.setAmount(500.0);
        depositStatus.setPayee("Cemre Kaplan");

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}