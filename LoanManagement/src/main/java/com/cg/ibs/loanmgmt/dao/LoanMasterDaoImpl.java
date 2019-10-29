package com.cg.ibs.loanmgmt.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.LoanStatus;
import com.cg.ibs.loanmgmt.util.JpaUtil;

public class LoanMasterDaoImpl implements LoanMasterDao {
	private static EntityManager entityManager;
	private LoanMaster loanMaster = null;

	public LoanMasterDaoImpl() {
		entityManager = JpaUtil.getEntityManger();
	}

	public LoanMaster getLoanByLoanNumber(BigInteger loanAccNum) {
		TypedQuery<LoanMaster> query = entityManager.createQuery("Select l.loan_account_num from Loan l",
				LoanMaster.class);
		List<LoanMaster> loans = query.getResultList();
		for (LoanMaster loanMasterTemp : loans) {
			if (loanMasterTemp.getLoanAccountNumber() == loanAccNum) {
				loanMaster = loanMasterTemp;
			}
		}
		return loanMaster;
	}

	public LoanMaster updateEmiDao(LoanMaster loanMaster) {
		loanMaster.setNumOfEmisPaid(loanMaster.getNumOfEmisPaid() + 1);
		loanMaster.setNextEmiDate(loanMaster.getNextEmiDate().plusMonths(1));
		return entityManager.merge(loanMaster);
	}

	public LoanMaster updateBalanceDao(LoanMaster loanMaster, BigDecimal balance) {
		loanMaster.setBalance(balance);
		return entityManager.merge(loanMaster);
	}

	public void updatePreClosureDao(LoanMaster loanMaster) {
		loanMaster.setLoanStatus(LoanStatus.PRE_CLOSURE_VERIFICATION);
		entityManager.merge(loanMaster);
	}

}
