package com.banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class LedgerTest {

    @Test
    void testRegisterAndGetAccount() {
        Ledger ledger = new Ledger();
        Account account = new Account("ACC-1", "Ahmed", 1000);
        
        ledger.registerAccount(account);
        Account retrieved = ledger.getAccount("ACC-1");
        
        assertEquals(account.getAccountId(), retrieved.getAccountId());
        assertEquals(1000, retrieved.getBalanceInCents());
    }

    @Test
    void testRegisterNullAccountThrowsException() {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.registerAccount(null));
    }

    @Test
    void testRegisterDuplicateAccountThrowsException() {
        Ledger ledger = new Ledger();
        Account account1 = new Account("ACC-1", "Ahmed", 1000);
        Account account2 = new Account("ACC-1", "Alice", 500);
        
        ledger.registerAccount(account1);
        assertThrows(IllegalStateException.class, () -> ledger.registerAccount(account2));
    }

    @Test
    void testGetNonExistentAccountThrowsException() {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.getAccount("ACC-999"));
    }

    @Test
    void testSuccessfulTransactionExecution() {
        Ledger ledger = new Ledger();
        Account acc1 = new Account("ACC-1", "Ahmed", 1000);
        Account acc2 = new Account("ACC-2", "Alice", 500);
        ledger.registerAccount(acc1);
        ledger.registerAccount(acc2);

        Transaction tx = new Transaction("TX-1", "ACC-1", "ACC-2", 400);
        ledger.processTransaction(tx);

        assertEquals(600, acc1.getBalanceInCents());
        assertEquals(900, acc2.getBalanceInCents());
        
        List<Transaction> history = ledger.getTransactionHistory();
        assertEquals(1, history.size());
        assertEquals("TX-1", history.get(0).getTransactionId());
    }

    @Test
    void testProcessNullTransactionThrowsException() {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.processTransaction(null));
    }

    @Test
    void testProcessTransactionMissingSourceThrowsException() {
        Ledger ledger = new Ledger();
        Account acc2 = new Account("ACC-2", "Alice", 500);
        ledger.registerAccount(acc2);

        Transaction tx = new Transaction("TX-1", "ACC-1", "ACC-2", 400);
        assertThrows(IllegalArgumentException.class, () -> ledger.processTransaction(tx));
    }

    @Test
    void testProcessTransactionMissingDestinationThrowsException() {
        Ledger ledger = new Ledger();
        Account acc1 = new Account("ACC-1", "Ahmed", 1000);
        ledger.registerAccount(acc1);

        Transaction tx = new Transaction("TX-1", "ACC-1", "ACC-2", 400);
        assertThrows(IllegalArgumentException.class, () -> ledger.processTransaction(tx));
    }

    @Test
    void testTransactionHistoryIsImmutablyProtected() {
        Ledger ledger = new Ledger();
        List<Transaction> history = ledger.getTransactionHistory();
        
        assertThrows(UnsupportedOperationException.class, () -> history.add(
            new Transaction("TX-1", "ACC-1", "ACC-2", 100)
        ));
    }
}