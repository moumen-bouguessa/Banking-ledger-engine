package com.banking;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final Ledger ledger = new Ledger();

    @PostMapping("/accounts")
    public String registerAccount(@RequestParam String accountId, @RequestParam String ownerName, @RequestParam long initialBalance) {
        Account account = new Account(accountId, ownerName, initialBalance);
        ledger.registerAccount(account);
        return "Account registered successfully: " + HtmlUtils.htmlEscape(accountId);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable String id) {
        return ledger.getAccount(id);
    }

    @PostMapping("/transactions")
    public String processTransaction(@RequestParam String txId, @RequestParam String sourceId, @RequestParam String destId, @RequestParam long amount) {
        Transaction tx = new Transaction(txId, sourceId, destId, amount);
        ledger.processTransaction(tx);
        return "Transaction processed successfully: " + HtmlUtils.htmlEscape(txId);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactionHistory() {
        return ledger.getTransactionHistory();
    }
}