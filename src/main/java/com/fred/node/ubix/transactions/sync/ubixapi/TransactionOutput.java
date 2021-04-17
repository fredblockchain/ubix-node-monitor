package com.fred.node.ubix.transactions.sync.ubixapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionOutput {

	private final String to;

	public TransactionOutput(@JsonProperty("to") String to) {

		this.to = to;
	}

	public String getTo() {
		return to;
	}
}
