package com.dmz.charging.station.finder.service.mapper;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.model.custom.StationDTO;
import com.dmz.charging.station.finder.service.model.domain.Station;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-28T15:36:22+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Homebrew)"
)
@Component
public class StationMapperImpl implements StationMapper {

    @Override
    public StationDTO stationToStationDTO(Station station) throws BusinessException {
        if ( station == null ) {
            return null;
        }

        StationDTO stationDTO = new StationDTO();

        stationDTO.setLatitude( StationMapper.toDegrees( station.getLatitudeInRadian() ) );
        stationDTO.setLongitude( StationMapper.toDegrees( station.getLongitudeInRadian() ) );
        stationDTO.setId( station.getId() );
        stationDTO.setName( station.getName() );

        return stationDTO;
    }
}
