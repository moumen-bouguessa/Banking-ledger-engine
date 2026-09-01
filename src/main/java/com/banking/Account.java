package com.banking;

/**
 * Represents a bank account with balance tracking and formal JML specifications.
 */
public class Account {

    private final String accountId;
    private final String ownerName;
    private long balanceInCents;

    //@ public invariant balanceInCents >= 0;

    /*@ 
      @ requires initialBalanceInCents >= 0;
      @ ensures getBalanceInCents() == initialBalanceInCents;
      @*/
    public Account(String accountId, String ownerName, long initialBalanceInCents) {
        if (initialBalanceInCents < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balanceInCents = initialBalanceInCents;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalanceInCents() {
        return balanceInCents;
    }

    /*@ 
      @ requires amountInCents > 0;
      @ ensures balanceInCents == \old(balanceInCents) + amountInCents;
      @*/
    public void deposit(long amountInCents) {
        if (amountInCents <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balanceInCents += amountInCents;
    }

    /*@ 
      @ requires amountInCents > 0;
      @ requires amountInCents <= balanceInCents;
      @ ensures balanceInCents == \old(balanceInCents) - amountInCents;
      @*/
    public void withdraw(long amountInCents) {
        if (amountInCents <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amountInCents > this.balanceInCents) {
            throw new IllegalStateException("Insufficient funds.");
        }
        this.balanceInCents -= amountInCents;
    }
}