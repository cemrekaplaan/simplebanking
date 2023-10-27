package com.eteration.simplebanking.model;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account { // Account class has public variables according to the given UML diagram in my case study.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String owner;
    public String accountNumber;
    public double balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    public List<Transaction> transactions = new ArrayList<>();

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public Account() {} // Default

    public interface AccountRepository extends JpaRepository<Account, Long> {
        Account findAccount(String accountNumber);
    }

    public void post(Transaction transaction){
        transactions.add(transaction);
    }

    public void credit(double amount){
        if (amount <= 0 ){ // Endpoints are limited.
            System.out.printf("Credit amount has to be positive.");
        } else {
            Transaction creditTransaction = new DepositTransaction(amount, LocalDateTime.now());
            this.balance += amount;
            post(creditTransaction); // Add it to the transaction's list.
        }
    }

    public void debit() throws InsufficientBalanceException {
        double amount = 0;
        if (amount <= 0 ){ //Endpoints are limited.
            System.out.printf("Debit amount has to be positive.");
        }
        else if (amount > balance){ //Endpoints are limited.
            throw new InsufficientBalanceException("Unfortunately, insufficient.");
        } else {
            Transaction debitTransaction = new WithdrawalTransaction(amount, LocalDateTime.now());
            this.balance += amount;
            post(debitTransaction); // Add it to the transaction's list.
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(int i) {
    }

    public void withdraw(int i) {
    }
}

