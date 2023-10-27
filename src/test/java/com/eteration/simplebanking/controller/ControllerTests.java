package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @Spy
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    // Test 1 - Checks the balance after each transactions.
    // Also it has findAccount() method.

    @Test
    public void givenId_Credit_thenReturnJson()
            throws Exception {

        Account account = new Account("Jim", "12345");

        doReturn(account).when(service).findAccount( "12345");
        ResponseEntity<TransactionStatus> result1 = controller.credit( "12345", new DepositTransaction(1000, LocalDateTime.now()));
        verify(service, times(1)).findAccount("12345");
        assertEquals("OK", result1.getBody().getStatus());
        assertEquals(1000.0, account.getBalance(), 0.001);

        ResponseEntity<TransactionStatus> result2 = controller.debit("12345", new WithdrawalTransaction(200, LocalDateTime.now()));
        verify(service, times(2)).findAccount("12345");
        assertEquals("OK", result2.getBody().getStatus());
        assertEquals(800.0, account.getBalance(), 0.001);

        ResponseEntity<TransactionStatus> result3 = controller.billPayment("12345",
                new BillPaymentTransaction("Vodafone", LocalDateTime.now(), 96.50));

        verify(service, times(3)).findAccount("12345");
        assertEquals("OK", result3.getBody().getStatus());
        assertEquals(703.50, account.getBalance(), 0.001);

    }

    // Test 2 - Credit the account with 1000.0 then debits 200.0
    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
            throws Exception {

        Account account = new Account("Jim", "12345");

        doReturn(account).when(service).findAccount( "12345");
        ResponseEntity<TransactionStatus> result = controller.credit( "12345", new DepositTransaction(1000.0, LocalDateTime.now()));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "12345", new WithdrawalTransaction(200, LocalDateTime.now()));
        verify(service, times(2)).findAccount("12345");
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
        assertEquals(950.0, account.getBalance(),0.001);

    }

    // Test 3 - Credit the account with 1000.0 then debits 5000.0
    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
            throws Exception {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            Account account = new Account("Jim", "12345");

            doReturn(account).when(service).findAccount( "12345");
            ResponseEntity<TransactionStatus> result = controller.credit( "12345", new DepositTransaction(1000.0, LocalDateTime.now()));
            assertEquals("OK", result.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).findAccount("12345");

            ResponseEntity<TransactionStatus> result2 = controller.debit( "12345", new WithdrawalTransaction(5000.0, LocalDateTime.now()));
        });
    }

    // Test 4 - It has doReturn method which checks if the correct account is returned.
    @Test
    public void givenId_GetAccount_thenReturnJson()
            throws Exception {

        Account account = new Account("Jim", "12345");

        doReturn(account).when(service).findAccount( "12345");
        ResponseEntity<Account> result = controller.getAccount( "12345");
        verify(service, times(1)).findAccount("12345");
        assertEquals(account, result.getBody());
    }

}
