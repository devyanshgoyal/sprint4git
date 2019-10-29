package com.cg.ibs.loanmgmt.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Transaction;

import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.LoanStatus;
import com.cg.ibs.loanmgmt.bean.LoanTypeBean;
import com.cg.ibs.loanmgmt.dao.LoanMasterDao;
import com.cg.ibs.loanmgmt.dao.LoanMasterDaoImpl;
import com.cg.ibs.loanmgmt.dao.LoanTypeDao;
import com.cg.ibs.loanmgmt.dao.LoanTypeDaoImpl;
import com.sun.xml.internal.stream.Entity;

public class CustomerServiceImpl implements CustomerService {
	private LoanMaster loanMaster = new LoanMaster();
	private static EntityManager entityManager;

	private static LoanMasterDao loanMasterDao = new LoanMasterDaoImpl();
	private static LoanTypeDao loanTypeDao = new LoanTypeDaoImpl();
	private static EntityTransaction transaction = entityManager.getTransaction();

	public boolean verifyLoanExist(BigInteger loanAccNumber) {
		boolean check = false;
		loanMaster = loanMasterDao.getLoanByLoanNumber(loanAccNumber);
		if (loanMaster == null) {
			check = true;
		}
		return check;
	}

	public boolean verifyEmiApplicable(BigInteger loanAccNumber) {
		boolean check = false;
		loanMaster = loanMasterDao.getLoanByLoanNumber(loanAccNumber);
		if (loanMaster.getLoanStatus().equals(LoanStatus.CLOSED)) {
			check = true;
		}
		return check;
	}

	public LoanMaster getLoanDetails(BigInteger loanAccNumber) {
		return loanMasterDao.getLoanByLoanNumber(loanAccNumber);
	}

	public LoanTypeBean getLoanTypeByTypeID(Integer typeId) {
		return loanTypeDao.getLoanTypeByTypeID(typeId);
	}

	public BigDecimal calculateBalance(LoanMaster loanMaster) {
		BigDecimal initialBalance = loanMaster.getBalance();
		Float rate = getLoanTypeByTypeID(loanMaster.getTypeId()).getInterestRate();
		System.out.println(rate); // Remove this
		BigDecimal paidInterest = (initialBalance
				.multiply(BigDecimal.valueOf((Math.pow(1 + rate / 100.0, 1.0 / 12.0)))))
						.subtract(loanMaster.getLoanAmount());
		BigDecimal paidPrincipal = loanMaster.getEmiAmount().subtract(paidInterest);
		BigDecimal newBalance = loanMaster.getBalance().subtract(paidPrincipal);
		return newBalance;

	}

	public LoanMaster updateEMI(LoanMaster loanMaster) {
		transaction.begin();
		LoanMaster loanMasterTemp = loanMasterDao.updateEmiDao(loanMaster);
		transaction.commit();
		return loanMasterTemp;
	}

	public LoanMaster updateBalance(LoanMaster loanMaster) {
		BigDecimal balance = calculateBalance(loanMaster);
		transaction.begin();
		LoanMaster loanMasterTemp = loanMasterDao.updateBalanceDao(loanMaster, balance);
		transaction.commit();
		return loanMasterTemp;
	}

	public boolean verifyPreClosureApplicable(BigInteger loanAccNum) {
		boolean check = false;
		loanMaster = loanMasterDao.getLoanByLoanNumber(loanAccNum);
		if (loanMaster.getNumOfEmisPaid() >= 0.25 * (loanMaster.getTotalNumOfEmis())) {
			check = true;
		}
		return check;

	}

	public void updatePreClosure(LoanMaster loanMaster) {
		transaction.begin();
		loanMasterDao.updatePreClosureDao(loanMaster);
		transaction.commit();
	}
}
