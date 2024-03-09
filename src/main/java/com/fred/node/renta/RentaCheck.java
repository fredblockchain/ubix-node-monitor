package com.fred.node.renta;


import com.fred.node.ubix.transactions.sync.UBIXTransactionSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RentaCheck {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UBIXTransactionSync ubixTransactionSync;


}
