package com.dmz.charging.station.finder.dal;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class DALConfig {
	
	 
	   
	   @Bean
	    public ChargingStationDAL getChargingStationDAL() {
	        return new ChargingStationDALimpl();
	    }
}
