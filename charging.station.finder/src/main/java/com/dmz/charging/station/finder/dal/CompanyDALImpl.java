package com.dmz.charging.station.finder.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.dmz.charging.station.finder.service.model.domain.Company;
@Component
class CompanyDALImpl implements CompanyDAL {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Override
	public List<Long> findChildCompaniesByParent(String companyCode) {
		
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("companyCode",companyCode);

		//@formatter:off
		StringBuilder sql = new StringBuilder();
		sql.append("WITH RECURSIVE category_path (id,parent_company_id  ) AS ")
			.append("	(")
			.append("	  SELECT id,parent_company_id ")
			.append("	  	FROM company ")
			.append("	  	WHERE company_code  = :companyCode")
			.append("	  UNION ALL")
			.append("	   SELECT c.id,c.parent_company_id ")
			.append("		FROM category_path AS cp JOIN company AS c")
			.append("		 ON cp.id = c.parent_company_id")
			.append("	)")
			.append("	SELECT id,parent_company_id FROM category_path");		 
		//@formatter:on
		return namedParameterJdbcTemplate.query(sql.toString(),namedParameters,(rs, rowNum)->  rs.getLong("id"));
		
	
	}

	@Override
	public Company findRootById(String companyID) {
		
		return companyRepository.findByCompanyCode(companyID);
	}

}
