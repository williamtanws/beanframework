package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.user.domain.Address;

@Component
public class AddressBasicPopulator extends AbstractPopulator<Address, AddressDto> implements Populator<Address, AddressDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AddressBasicPopulator.class);

	@Override
	public void populate(Address source, AddressDto target) throws PopulatorException {
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
	}

}
