package com.fred.node.ubix.transactions.sync;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UBIXUBIXTransactionPageTest {

    @Test
    void testSynchronizeOK() {
        UBIXTransactionPage p = new UBIXTransactionPage("Ux5d216463243703991f272b2a92e99c85769d81a6",0);
        try {
            p.synchronize();
        } catch (IOException e) {
            assertTrue(false);
        }
        assertTrue(p.getTransactions().size()>0);
    }

    @Test
    void testSynchronizeNoTransactions() {
        UBIXTransactionPage p = new UBIXTransactionPage("Ux33333333",0);
        try {
            p.synchronize();
        } catch (IOException e) {
            assertTrue(false);
        }
        assertEquals(0, p.getTransactions().size() );
    }


}