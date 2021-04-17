package com.fred.node.ubix.transactions.sync.ubixapi;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UBIXTransaction {

	private final String id;
	private final Long timestamp;
	private final Long value;
	private final ArrayList<TransactionInput> inputs;
	private final ArrayList<TransactionOutput> outputs;

	public UBIXTransaction(
			@JsonProperty("hash") String hash,
			@JsonProperty("timestamp") Long timestamp,
			@JsonProperty("value") Long value,
			@JsonProperty("inputs") ArrayList<TransactionInput> inputs,
			@JsonProperty("outputs") ArrayList<TransactionOutput> outputs) {
		this.id = hash;
		this.timestamp = timestamp;
		this.value = value;
		this.inputs = inputs;
		this.outputs = outputs;
	}


	public String getId() {
		return id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public Long getValue() {
		return value;
	}

	public ArrayList<TransactionInput> getInputs() {
		return inputs;
	}

	public ArrayList<TransactionOutput> getOutputs() {
		return outputs;
	}
}
