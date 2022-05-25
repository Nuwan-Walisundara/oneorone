package com.dmz.charging.station.finder.dal;

import java.util.List;

import com.dmz.charging.station.finder.service.model.domain.Company;

public interface CompanyDAL {
	List<Long> findChildCompaniesByParent(String companyID);
	Company findRootById(String companyID);

}
