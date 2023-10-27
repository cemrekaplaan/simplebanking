package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// This class is a placeholder you can change the complete implementation
@Service
public class AccountService {
    private final Account.AccountRepository accountRepository;
    private final Transaction.TransactionRepository transactionRepository;
    private Object payee;
    private Transaction depositTransaction;

    @Autowired
    public AccountService(Account.AccountRepository accountRepository, Transaction.TransactionRepository transactionRepository, Object payee) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.payee = payee;

    }
    
    public void processDeposit(Account account, double amount){
        Transaction transaction = new DepositTransaction(amount, LocalDateTime.now());
        account.post(transaction);
        transactionRepository.save(depositTransaction); // Saved transaction
        accountRepository.save(account); // Saved account to update account's balance in the database
    }
    public void processWithdrawal(Account account, double amount) throws InsufficientBalanceException {
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount, LocalDateTime.now());
        account.post(withdrawalTransaction);
        transactionRepository.save(withdrawalTransaction); // Saved transaction
        accountRepository.save(account); // Saved account to update account's balance in the database
    }
    public void processBillPayment(Account account, double amount, String payee) throws InsufficientBalanceException {
        BillPaymentTransaction billPaymentTransaction = new BillPaymentTransaction(payee, LocalDateTime.now(), amount);
        billPaymentTransaction.process(); // Bill Payment Transaction processed.
        transactionRepository.save(billPaymentTransaction); // Saved transaction
        accountRepository.save(account); // Saved account to update account's balance in the database
    }
    public Account findAccount(String accountNumber) {
        return accountRepository.findAccount(accountNumber);
    }
    public void processCheckTransactions(Account account, int amount) throws InsufficientBalanceException {
        CheckTransactions checkTransactions = new CheckTransactions(amount, account);
        checkTransactions.process(); // Check Transactions processed.
        transactionRepository.save(checkTransactions); // Saved transaction
        accountRepository.save(account);
    }
}