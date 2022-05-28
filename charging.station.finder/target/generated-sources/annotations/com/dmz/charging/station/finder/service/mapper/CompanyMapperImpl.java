package com.dmz.charging.station.finder.service.mapper;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.model.custom.CompanyDto;
import com.dmz.charging.station.finder.service.model.custom.StationDTO;
import com.dmz.charging.station.finder.service.model.domain.Company;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-28T15:36:22+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Homebrew)"
)
@Component
public class CompanyMapperImpl extends CompanyMapper {

    @Override
    public CompanyDto CompanyToCompanyDto(Company company) throws BusinessException {
        if ( company == null ) {
            return null;
        }

        int parentId = 0;
        String name = null;

        Long id = companyParentId( company );
        if ( id != null ) {
            parentId = id.intValue();
        }
        name = company.getName();

        List<StationDTO> stations = null;
        List<CompanyDto> childCompany = null;

        CompanyDto companyDto = new CompanyDto( parentId, name, stations, childCompany );

        return companyDto;
    }

    private Long companyParentId(Company company) {
        if ( company == null ) {
            return null;
        }
        Company parent = company.getParent();
        if ( parent == null ) {
            return null;
        }
        Long id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
