package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.Address;

@Component
public class AddressFullPopulator extends AbstractPopulator<Address, AddressDto> implements Populator<Address, AddressDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AddressFullPopulator.class);
	
	@Autowired
	private CountryBasicPopulator countryBasicPopulator;
	
	@Autowired
	private RegionBasicPopulator regionBasicPopulator;
	
	@Autowired
	private UserBasicPopulator userBasicPopulator;

	@Override
	public void populate(Address source, AddressDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setStreetName(target.getStreetName());
			target.setStreetNumber(target.getStreetNumber());
			target.setPostalCode(target.getPostalCode());
			target.setTown(target.getTown());
			target.setCompany(target.getCompany());
			target.setPhone1(target.getPhone1());
			target.setPhone2(target.getPhone2());
			target.setEmail(target.getEmail());
			target.setPoBox(target.getPoBox());
			target.setFax(target.getFax());
			target.setTitle(target.getTitle());
			target.setLastName(target.getLastName());
			target.setFirstName(target.getFirstName());
			target.setMiddleName(target.getMiddleName());
			target.setMiddleName2(target.getMiddleName2());
			target.setDepartment(target.getDepartment());
			target.setBuilding(target.getBuilding());
			target.setApartment(target.getApartment());
			target.setDistrict(target.getDistrict());
			
			target.setCountry(modelService.getDto(source.getCountry(), CompanyDto.class, new DtoConverterContext(countryBasicPopulator)));
			target.setRegion(modelService.getDto(source.getRegion(), CompanyDto.class, new DtoConverterContext(regionBasicPopulator)));
			target.setOwner(modelService.getDto(source.getOwner(), UserDto.class, new DtoConverterContext(userBasicPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
