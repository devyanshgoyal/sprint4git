package com.cg.ibs.loanmgmt.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.LoanTypeBean;

public interface CustomerService {
	public boolean verifyLoanExist(BigInteger loanAccNumber);

	public boolean verifyEmiApplicable(BigInteger loanAccNumber);

	public LoanMaster getEmiDetails(BigInteger loanAccNumber);

	public LoanTypeBean getLoanTypeByTypeID(Integer typeId);

	public BigDecimal calculateBalance(LoanMaster loanMaster);

	public LoanMaster updateEMI(LoanMaster loanMaster);

	public LoanMaster updateBalance(LoanMaster loanMaster);
}
