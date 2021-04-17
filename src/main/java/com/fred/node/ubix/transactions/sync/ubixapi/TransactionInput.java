package com.fred.node.ubix.transactions.sync.ubixapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionInput {

	private final String from;
	
	public TransactionInput(@JsonProperty("from") String from) {

		this.from = from;
	}


	public String getFrom() {
		return from;
	}
}
