package com.dmz.charging.station.finder.dal;

import java.util.List;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;
import com.dmz.charging.station.finder.service.model.domain.Station;

public interface ChargingStationDAL {
	List<Station> loadNearestStations(StationFinderDTO searchDto,List<Long> companyList)throws BusinessException;

}
