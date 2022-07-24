package com.dmz.charging.station.finder.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmz.charging.station.finder.service.model.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findByCompanyCode(String companyCode);

}
