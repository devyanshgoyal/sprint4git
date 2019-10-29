package com.cg.ibs.loanmgmt.dao;

import javax.persistence.EntityManager;

import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.LoanTypeBean;
import com.cg.ibs.loanmgmt.util.JpaUtil;

public class LoanTypeDaoImpl implements LoanTypeDao {
	private static EntityManager entityManager;
	private LoanMaster loanMaster = null;

	public LoanTypeDaoImpl() {
		entityManager = JpaUtil.getEntityManger();
	}

	public LoanTypeBean getLoanTypeByTypeID(Integer typeId) {
		return entityManager.find(LoanTypeBean.class, typeId);
	}

}
