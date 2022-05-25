package com.dmz.charging.station.finder.service;

import com.dmz.charging.station.finder.service.model.custom.SearchResult;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;

public interface FinderService {
	SearchResult find(StationFinderDTO searchDto);

}
