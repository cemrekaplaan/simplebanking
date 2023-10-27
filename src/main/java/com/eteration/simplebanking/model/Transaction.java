package com.eteration.simplebanking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public abstract class Transaction {

    @Id
    private LocalDateTime date;
    private double amount;
    Transaction depositTransaction;

    public Transaction(double amount, LocalDateTime now) {
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public Transaction() {} // Default

    public Transaction(double amount, Account account) {
    }

    public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        List<Transaction> findAccount(Account account);
    }

    public abstract void process() throws InsufficientBalanceException; // // Overriden by it's subclasses.

    public LocalDateTime getDate() {
        return date;
    }

    public int getAmount() {
        return (int) amount;
    }
}
