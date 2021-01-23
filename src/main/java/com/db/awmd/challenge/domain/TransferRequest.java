package com.db.awmd.challenge.domain;

public class TransferRequest {

	private String accountFrom;
	private String accountTo;
	private String transferAmount ;
	
	public TransferRequest() {
	}
	
	public TransferRequest(String accountFrom, String accountTo, String transferAmount) {
		super();
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.transferAmount = transferAmount;
	}

	public String getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	@Override
	public String toString() {
		return "TransferRequest [accountFrom=" + accountFrom + ", accountTo=" + accountTo + ", transferAmount="
				+ transferAmount + "]";
	}
	
}
