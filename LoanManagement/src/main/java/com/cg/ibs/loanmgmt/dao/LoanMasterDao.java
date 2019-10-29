package com.cg.ibs.loanmgmt.dao;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.cg.ibs.loanmgmt.bean.LoanMaster;

public interface LoanMasterDao {
	public LoanMaster getLoanByLoanNumber(BigInteger loanAccNum);

	public LoanMaster updateEmiDao(LoanMaster loanMaster);

	public LoanMaster updateBalanceDao(LoanMaster loanMaster, BigDecimal balance);
}
