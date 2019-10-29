package com.cg.ibs.loanmgmt.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class LoanTypeBean {
	@Id
	@Column(name = "type_Id", nullable = false)
	private Integer typeId;
	@Column(name = "loan_type", nullable = false)
	private String loanType;
	@Column(name = "interest_rate", nullable = false)
	private Float interestRate;
	@Column(name = "maximum_limit", nullable = false)
	private Long maximumLimit;
	@Column(name = "minimum_limit", nullable = false)
	private Long minimumLimit;
	@OneToMany(mappedBy = "typeId")
	private List<LoanMaster> loanMasters;
	

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public Long getMaximumLimit() {
		return maximumLimit;
	}

	public void setMaximumLimit(Long maximumLimit) {
		this.maximumLimit = maximumLimit;
	}

	public Long getMinimumLimit() {
		return minimumLimit;
	}

	public void setMinimumLimit(Long minimumLimit) {
		this.minimumLimit = minimumLimit;
	}

}
