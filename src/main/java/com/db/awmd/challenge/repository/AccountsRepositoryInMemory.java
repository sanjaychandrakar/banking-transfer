package com.db.awmd.challenge.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.db.awmd.challenge.constants.TransferConstants;
import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferResponse;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public void createAccount(Account account) throws DuplicateAccountIdException {
    Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
    if (previousAccount != null) {
      throw new DuplicateAccountIdException(
        "Account id " + account.getAccountId() + " already exists!");
    }
  }

  @Override
  public Account getAccount(String accountId) {
    return accounts.get(accountId);
  }

  @Override
  public void clearAccounts() {
    accounts.clear();
  }
  
  @Override
  public TransferResponse performTransfer(String from, String to, String amount) {
	  if(StringUtils.isEmpty(from) || StringUtils.isEmpty(to) || StringUtils.isEmpty(amount)) {
		  return new TransferResponse(TransferConstants.INCORRECT_REQUEST,HttpStatus.BAD_REQUEST);
	  }
	  Account fromAct = getAccount(from);
	  Account toAct = getAccount(to);
	  if(fromAct == null) {
		  return new TransferResponse(TransferConstants.INVALID_FROM_ACCOUNT,HttpStatus.BAD_REQUEST);
	  }else if(toAct ==null) {
		  return new TransferResponse(TransferConstants.INVALID_TO_ACCOUNT,HttpStatus.BAD_REQUEST);
	  }

	  BigDecimal amountToTransfer = BigDecimal.ZERO;
	  try {
		  amountToTransfer = BigDecimal.valueOf(Double.valueOf(amount));
	  }catch(NumberFormatException ex) {
		  return new TransferResponse(TransferConstants.INVALID_TRANSFER_AMT,HttpStatus.BAD_REQUEST);
	  }

	  if(fromAct.getBalance().compareTo(amountToTransfer) < 0) {
		  return new TransferResponse(TransferConstants.INSUFFICIENT_BALANCE,HttpStatus.BAD_REQUEST);
	  }else {
		  fromAct.setBalance(fromAct.getBalance().subtract(amountToTransfer));
		  toAct.setBalance(toAct.getBalance().add(amountToTransfer));
		  return new TransferResponse(TransferConstants.SUCCESS,HttpStatus.OK);
	  }
  }

}
