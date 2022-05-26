package com.dmz.charging.station.finder.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;
import com.dmz.charging.station.finder.service.model.domain.Station;

class ChargingStationDALimpl implements ChargingStationDAL {
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<Station> loadNearestStations(StationFinderDTO searchDto, List<Long> companyList)throws BusinessException {
		
		//@formatter:off
		MapSqlParameterSource namedParameters = new MapSqlParameterSource()
												.addValue("srcLatitude",searchDto.getLatitudeInRadian())
												.addValue("srcLongitude", searchDto.getLongitudeInRadian())
											
												.addValue("compList", companyList);

		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name,latitude,longitude,company_id ")
			.append(" FROM station WHERE company_id in(:compList) ");
		
		if(searchDto.getPreferredRadius()>0) {
			namedParameters.addValue("preferdDistance", searchDto.getPreferredRadius());
			
			sql	.append(" AND acos(")
				.append("		sin(:srcLatitude) * sin(latitude) + cos(:srcLatitude) * cos(latitude) * cos(:srcLongitude-longitude )")
				.append(" 		) * 6371 <= :preferdDistance ");
		}
		
		 return namedParameterJdbcTemplate.query(sql.toString(),
				 									namedParameters,
				 									(rs, rowNum)->  new Station(rs.getLong("id"), 
				 																rs.getString("name"), 
				 																rs.getDouble("latitude"), 
				 																rs.getDouble("longitude"), 
				 																rs.getLong("company_id")));
		//@formatter:on
	}

}
