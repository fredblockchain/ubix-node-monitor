package com.fred.node.ubix.transactions.sync.ubixapi;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionPage {

	public final Long balance;
	public final Long recordsCount;
	public final ArrayList<UBIXTransaction> txInfoDTOs;
	
	
	public TransactionPage(@JsonProperty("balance") Long balance,
			@JsonProperty("recordsCount") Long recordsCount,
			@JsonProperty("txInfoDTOs") ArrayList<UBIXTransaction> txInfoDTOs) {
		this.balance = balance;
		this.recordsCount = recordsCount;
		this.txInfoDTOs = txInfoDTOs;
	}
}
