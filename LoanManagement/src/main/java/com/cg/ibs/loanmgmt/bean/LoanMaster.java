package com.cg.ibs.loanmgmt.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Loan_Application")
public class LoanMaster {
	@Id
	@Column(name = "application_num", nullable = false)
	private BigInteger applicationNumber;
	@Column(nullable = false)
	private BigInteger uci;
	@Column(name = "loan_amount", nullable = false)
	private BigDecimal loanAmount;
	@Column(name = "loan_tenure", nullable = false)
	private int loanTenure;
	@Column(nullable = false)
	private BigDecimal balance;
	@Column(name = "applied_date", nullable = false)
	private LocalDate appliedDate;
	@Column(name = "total_num_of_emis", nullable = false)
	private Integer totalNumOfEmis;
	@Column(name = "num_of_emis_paid", nullable = false)
	private Integer numOfEmisPaid;
	@Column(name = "type_id", nullable = false)
	private Integer typeId;
	@Column(name = "emi_amount", nullable = false)
	private BigDecimal emiAmount;
	@Column(name = "document_Id", nullable = false)
	private Long documentId;
	@Enumerated(EnumType.STRING)
	@Column(name = "loan_status", nullable = false, length = 20)
	private LoanStatus loanStatus;
	private LocalDate nextEmiDate;
	@Column(name = "loan_account_num", unique = true)
	private BigInteger loanAccountNumber;
	@ManyToOne
	private LoanTypeBean loanType;

	public LoanMaster() {
		super();
	}

	public BigInteger getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(BigInteger applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public BigInteger getUci() {
		return uci;
	}

	public void setUci(BigInteger uci) {
		this.uci = uci;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public int getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(int loanTenure) {
		this.loanTenure = loanTenure;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public Integer getTotalNumOfEmis() {
		return totalNumOfEmis;
	}

	public void setTotalNumOfEmis(Integer totalNumOfEmis) {
		this.totalNumOfEmis = totalNumOfEmis;
	}

	public Integer getNumOfEmisPaid() {
		return numOfEmisPaid;
	}

	public void setNumOfEmisPaid(Integer numOfEmisPaid) {
		this.numOfEmisPaid = numOfEmisPaid;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public BigDecimal getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(BigDecimal emiAmount) {
		this.emiAmount = emiAmount;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	public LocalDate getNextEmiDate() {
		return nextEmiDate;
	}

	public void setNextEmiDate(LocalDate nextEmiDate) {
		this.nextEmiDate = nextEmiDate;
	}

	public BigInteger getLoanAccountNumber() {
		return loanAccountNumber;
	}

	public void setLoanAccountNumber(BigInteger loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}

}
