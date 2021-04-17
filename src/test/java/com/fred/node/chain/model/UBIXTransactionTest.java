package com.fred.node.chain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UBIXTransactionTest {

    @Test
    void testEqualsOK() {
        Transaction t1 = new Transaction("","","id1","","", 1, null);
        Transaction t2 = new Transaction("","","id1","","", 1, null);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testEqualsKO() {
        Transaction t1 = new Transaction("","","id1","","", 1, null);
        Transaction t2 = new Transaction("","","id2","","", 1, null);
        assertFalse(t1.equals(t2));
    }

    @Test
    void testEqualsNull() {
        Transaction t1 = new Transaction("","","id1","","", 1, null);
        assertFalse(t1.equals(null));
    }

    @Test
    void testEqualsOtherType() {
        Transaction t1 = new Transaction("","","id1","","", 1, null);
        assertFalse(t1.equals("other object type"));
    }
}