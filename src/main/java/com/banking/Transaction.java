package com.banking;

import java.time.Instant;

/**
 * Represents an immutable financial transaction record between two accounts.
 */
public class Transaction {

    private final String transactionId;
    private final String sourceAccountId;
    private final String destinationAccountId;
    private final long amountInCents;
    private final Instant timestamp;

    /*@ 
      @ public invariant amountInCents > 0;
      @ public invariant !sourceAccountId.equals(destinationAccountId);
      @*/

    /*@ 
      @ requires transactionId != null && !transactionId.trim().isEmpty();
      @ requires sourceAccountId != null && !sourceAccountId.trim().isEmpty();
      @ requires destinationAccountId != null && !destinationAccountId.trim().isEmpty();
      @ requires !sourceAccountId.equals(destinationAccountId);
      @ requires amountInCents > 0;
      @ ensures getTransactionId().equals(transactionId);
      @ ensures getSourceAccountId().equals(sourceAccountId);
      @ ensures getDestinationAccountId().equals(destinationAccountId);
      @ ensures getAmountInCents() == amountInCents;
      @*/
    public Transaction(String transactionId, String sourceAccountId, String destinationAccountId, long amountInCents) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or empty.");
        }
        if (sourceAccountId == null || sourceAccountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Source account ID cannot be null or empty.");
        }
        if (destinationAccountId == null || destinationAccountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination account ID cannot be null or empty.");
        }
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("Source and destination accounts must be different.");
        }
        if (amountInCents <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amountInCents = amountInCents;
        this.timestamp = Instant.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public long getAmountInCents() {
        return amountInCents;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}