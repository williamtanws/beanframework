package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.data.RegionDto;
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
	
	@Autowired
	private AddressBasicPopulator addressBasicPopulator;

	@Override
	public void populate(Address source, AddressDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getStreetName());
			target.setStreetNumber(source.getStreetNumber());
			target.setPostalCode(source.getPostalCode());
			target.setTown(source.getTown());
			target.setCompany(source.getCompany());
			target.setPhone1(source.getPhone1());
			target.setPhone2(source.getPhone2());
			target.setMobilePhone(source.getMobilePhone());
			target.setEmail(source.getEmail());
			target.setPoBox(source.getPoBox());
			target.setFax(source.getFax());
			target.setTitle(source.getTitle());
			target.setLastName(source.getLastName());
			target.setFirstName(source.getFirstName());
			target.setMiddleName(source.getMiddleName());
			target.setMiddleName2(source.getMiddleName2());
			target.setDepartment(source.getDepartment());
			target.setBuilding(source.getBuilding());
			target.setApartment(source.getApartment());
			target.setDistrict(source.getDistrict());
			
			target.setCountry(modelService.getDto(source.getCountry(), CountryDto.class, new DtoConverterContext(countryBasicPopulator)));
			target.setRegion(modelService.getDto(source.getRegion(), RegionDto.class, new DtoConverterContext(regionBasicPopulator)));
			target.setOwner(modelService.getDto(source.getOwner(), UserDto.class, new DtoConverterContext(userBasicPopulator)));
			target.setShippingAddress(modelService.getDto(source.getShippingAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setBillingAddress(modelService.getDto(source.getBillingAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setContactAddress(modelService.getDto(source.getContactAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setDefaultPaymentAddress(modelService.getDto(source.getDefaultPaymentAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setDefaultShipmentAddress(modelService.getDto(source.getDefaultShipmentAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
