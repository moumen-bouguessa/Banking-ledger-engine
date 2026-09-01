package com.banking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages account balances, processes transactions, and maintains double-entry audit history.
 */
public class Ledger {

    private final Map<String, Account> accounts = new HashMap<>();
    private final List<Transaction> transactionHistory = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    /*@ 
      @ public invariant accounts != null;
      @ public invariant transactionHistory != null;
      @*/

    public void registerAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        lock.lock();
        try {
            if (accounts.containsKey(account.getAccountId())) {
                throw new IllegalStateException("Account ID already registered.");
            }
            accounts.put(account.getAccountId(), account);
        } finally {
            lock.unlock();
        }
    }

    public Account getAccount(String accountId) {
        lock.lock();
        try {
            Account account = accounts.get(accountId);
            if (account == null) {
                throw new IllegalArgumentException("Account not found.");
            }
            return account;
        } finally {
            lock.unlock();
        }
    }

    /*@ 
      @ requires transaction != null;
      @*/
    public void processTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }

        lock.lock();
        try {
            Account source = accounts.get(transaction.getSourceAccountId());
            Account destination = accounts.get(transaction.getDestinationAccountId());

            if (source == null) {
                throw new IllegalArgumentException("Source account does not exist.");
            }
            if (destination == null) {
                throw new IllegalArgumentException("Destination account does not exist.");
            }

            // Perform double-entry transfer (atomically)
            source.withdraw(transaction.getAmountInCents());
            destination.deposit(transaction.getAmountInCents());

            // Record into audit log
            transactionHistory.add(transaction);
        } finally {
            lock.unlock();
        }
    }

    public List<Transaction> getTransactionHistory() {
        lock.lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(transactionHistory));
        } finally {
            lock.unlock();
        }
    }
}