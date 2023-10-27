package com.eteration.simplebanking.controller;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
public class AccountController {
    private Account account;
    private AccountService accountService;

    @Autowired
    public AccountController(Account account) {
        this.account = account;
    }
    public AccountController(AccountService accountService) { this.accountService = accountService; }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction request) {
        try {
            Account account = accountService.findAccount(accountNumber);
            accountService.processDeposit(account, request.getAmount());
            return ResponseEntity.ok(new TransactionStatus("True", "Deposit", request.getAmount(), null));
        } catch (Exception e) {
            return ResponseEntity.ok(new TransactionStatus("False", "Deposit", request.getAmount(), null));
        }
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction request) {
        try {
            Account account = accountService.findAccount(accountNumber);
            accountService.processWithdrawal(account, request.getAmount());
            return ResponseEntity.ok(new TransactionStatus("True", "Withdrawal", request.getAmount(), null));
        } catch (Exception e) {
            return ResponseEntity.ok(new TransactionStatus("False", "Withdrawal", request.getAmount(), null));
        }
    }

    @PostMapping("/billPayment/{accountNumber}")
    public ResponseEntity<TransactionStatus> billPayment(@PathVariable String accountNumber, @RequestBody BillPaymentTransaction request) {
        try {
            Account account = accountService.findAccount(accountNumber);
            accountService.processBillPayment(account, request.getAmount(), request.getPayee());
            return ResponseEntity.ok(new TransactionStatus("True", "Bill Payment", request.getAmount(), request.getPayee()));
        } catch (Exception e) {
            return ResponseEntity.ok(new TransactionStatus("False", "Bill Payment", request.getAmount(), request.getPayee()));
        }
    }

    @PostMapping("/checkTransaction/{accountNumber}")
    public ResponseEntity<TransactionStatus> checkTransaction(@PathVariable String accountNumber, @RequestBody CheckTransactions request) {
        try {
            Account account = accountService.findAccount(accountNumber);
            accountService.processCheckTransactions(account, request.getAmount());
            return ResponseEntity.ok(new TransactionStatus("True", "Check Transaction", request.getAmount(), null));
        } catch (Exception e) {
            return ResponseEntity.ok(new TransactionStatus("False", "Check Transaction", request.getAmount(), null));
        }
    }

        @GetMapping("/{accountNumber}")
        public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
            Account account = accountService.findAccount(accountNumber);
            return ResponseEntity.ok(account);
        }
}