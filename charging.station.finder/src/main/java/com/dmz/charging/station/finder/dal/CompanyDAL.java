package com.dmz.charging.station.finder.dal;

import java.util.List;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.model.domain.Company;

public interface CompanyDAL {
	List<Long> findChildCompaniesByParent(String companyID)throws BusinessException;
	Company findRootById(String companyID) throws BusinessException;

}
