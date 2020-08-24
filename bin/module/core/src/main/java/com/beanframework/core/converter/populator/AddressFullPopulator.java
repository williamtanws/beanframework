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
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.User;

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
			target.setStreetName(source.getStreetName());
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

			if (source.getCountry() != null) {
				Country entity = modelService.findOneByUuid(source.getCountry(), Country.class);
				target.setCountry(modelService.getDto(entity, CountryDto.class, new DtoConverterContext(countryBasicPopulator)));
			}
			if (source.getRegion() != null) {
				Region entity = modelService.findOneByUuid(source.getRegion(), Region.class);
				target.setRegion(modelService.getDto(entity, RegionDto.class, new DtoConverterContext(regionBasicPopulator)));
			}
			if (source.getOwner() != null) {
				User entity = modelService.findOneByUuid(source.getOwner(), User.class);
				target.setOwner(modelService.getDto(entity, UserDto.class, new DtoConverterContext(userBasicPopulator)));
			}
			if (source.getShippingAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getShippingAddress(), Address.class);
				target.setShippingAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getBillingAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getBillingAddress(), Address.class);
				target.setBillingAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getContactAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getContactAddress(), Address.class);
				target.setContactAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getDefaultPaymentAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getDefaultPaymentAddress(), Address.class);
				target.setDefaultPaymentAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getDefaultShipmentAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getDefaultShipmentAddress(), Address.class);
				target.setDefaultShipmentAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
