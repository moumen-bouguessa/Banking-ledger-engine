package com.banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    void testValidTransactionCreation() {
        Transaction tx = new Transaction("TX-101", "ACC-1", "ACC-2", 500);
        assertEquals("TX-101", tx.getTransactionId());
        assertEquals("ACC-1", tx.getSourceAccountId());
        assertEquals("ACC-2", tx.getDestinationAccountId());
        assertEquals(500, tx.getAmountInCents());
        // Calls the method to fix Line 73 NO_COVERAGE
        assertNotNull(tx.getTimestamp()); 
    }

    @Test
    void testInvalidTransactionIdThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction(null, "ACC-1", "ACC-2", 500));
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("  ", "ACC-1", "ACC-2", 500));
    }

    @Test
    void testInvalidSourceAccountIdThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", null, "ACC-2", 500));
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", " ", "ACC-2", 500));
    }

    @Test
    void testInvalidDestinationAccountIdThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", "ACC-1", null, 500));
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", "ACC-1", "  ", 500));
    }

    @Test
    void testSelfTransferThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", "ACC-1", "ACC-1", 500));
    }

    @Test
    void testNonPositiveAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", "ACC-1", "ACC-2", -50));
    }

    @Test
    void testZeroAmountThrowsException() {
        // Kills the boundary mutant on Line 45
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", "ACC-1", "ACC-2", 0));
    }
    
    @Test
    void testEmptyStringValidations() {
        // Covers the exact empty string "" paths for the pink lines
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("", "ACC-1", "ACC-2", 500));
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", "", "ACC-2", 500));
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("TX-101", "ACC-1", "", 500));
    }
}