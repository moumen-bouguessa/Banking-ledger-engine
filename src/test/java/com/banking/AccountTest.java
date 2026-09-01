package com.banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    void testGettersAndConstructor() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        assertEquals("ACC-1", account.getAccountId());
        assertEquals("Ahmed", account.getOwnerName());
        assertEquals(1000, account.getBalanceInCents());
    }

    @Test
    void testZeroInitialBalanceIsAllowed() {
        // Tests the boundary value 0 for constructor
        Account account = new Account("ACC-1", "Ahmed", 0);
        assertEquals(0, account.getBalanceInCents());
    }

    @Test
    void testNegativeInitialBalanceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Account("ACC-1", "Ahmed", -100));
    }

    @Test
    void testDepositIncreasesBalance() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        account.deposit(500);
        assertEquals(1500, account.getBalanceInCents());
    }

    @Test
    void testZeroDepositThrowsException() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        // Kills mutant changing <= 0 to < 0 in deposit
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
    }

    @Test
    void testNegativeDepositThrowsException() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
    }

    @Test
    void testWithdrawDecreasesBalance() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        account.withdraw(400);
        assertEquals(600, account.getBalanceInCents());
    }

    @Test
    void testZeroWithdrawThrowsException() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
    }

    @Test
    void testNegativeWithdrawThrowsException() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        // Kills mutant changing <= 0 to < 0 in withdraw
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50));
    }

    @Test
    void testOverdrawThrowsException() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        assertThrows(IllegalStateException.class, () -> account.withdraw(2000));
    }
    
    @Test
    void testWithdrawExactBalanceAllowed() {
        Account account = new Account("ACC-1", "Ahmed", 1000);
        // Withdraw exact balance down to 0
        account.withdraw(1000);
        assertEquals(0, account.getBalanceInCents());
    }
}