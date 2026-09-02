package com.banking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LedgerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterAccountAndGet() throws Exception {
        mockMvc.perform(post("/api/ledger/accounts")
                .param("accountId", "ACC123")
                .param("ownerName", "Alice")
                .param("initialBalance", "500"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/ledger/accounts/ACC123"))
                .andExpect(status().isOk());
    }

    @Test
    void testProcessTransactionAndHistory() throws Exception {
        // Register source account
        mockMvc.perform(post("/api/ledger/accounts")
                .param("accountId", "ACC999")
                .param("ownerName", "Bob")
                .param("initialBalance", "1000"));

        // Register destination account
        mockMvc.perform(post("/api/ledger/accounts")
                .param("accountId", "ACC888")
                .param("ownerName", "Charlie")
                .param("initialBalance", "500"));

        // Process transaction between different accounts
        mockMvc.perform(post("/api/ledger/transactions")
                .param("txId", "TX001")
                .param("sourceId", "ACC999")
                .param("destId", "ACC888")
                .param("amount", "100"))
                .andExpect(status().isOk());

        // Get history
        mockMvc.perform(get("/api/ledger/transactions"))
                .andExpect(status().isOk());
    }
}