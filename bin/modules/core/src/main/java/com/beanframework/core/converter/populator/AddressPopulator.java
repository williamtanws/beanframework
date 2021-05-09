package com.beanframework.core.converter.populator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;

@Component
public class AddressPopulator extends AbstractPopulator<Address, AddressDto> implements Populator<Address, AddressDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AddressPopulator.class);

	@Override
	public void populate(Address source, AddressDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
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

			target.setCountry(populateCountry(source.getCountry()));
			target.setRegion(populateRegion(source.getRegion()));
			target.setOwner(populateUser(source.getOwner()));

			target.setShippingAddress(populateAddress(source.getShippingAddress()));
			target.setBillingAddress(populateAddress(source.getBillingAddress()));
			target.setContactAddress(populateAddress(source.getContactAddress()));
			target.setDefaultPaymentAddress(populateAddress(source.getDefaultPaymentAddress()));
			target.setDefaultShipmentAddress(populateAddress(source.getDefaultShipmentAddress()));

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public CountryDto populateCountry(UUID uuid) throws PopulatorException {
		if(uuid == null)
			return null;
		
		try {
			Country source = modelService.findOneByUuid(uuid, Country.class);
			if(source == null) {
				return null;
			}
			
			CountryDto target = new CountryDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setName(source.getName());
			target.setActive(source.getActive());
			
			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public RegionDto populateRegion(UUID uuid) throws PopulatorException {
		if(uuid == null)
			return null;
		
		try {
			Region source = modelService.findOneByUuid(uuid, Region.class);
			if(source == null) {
				return null;
			}
			
			RegionDto target = new RegionDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setName(source.getName());
			target.setActive(source.getActive());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public AddressDto populateAddress(UUID uuid) throws PopulatorException {
		if(uuid == null)
			return null;
		
		try {
			Address source = modelService.findOneByUuid(uuid, Address.class);
			if(source == null) {
				return null;
			}
			
			AddressDto target = new AddressDto();
			populateGeneric(source, target);
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

			target.setCountry(populateCountry(source.getCountry()));
			target.setRegion(populateRegion(source.getRegion()));
			target.setOwner(populateUser(source.getOwner()));

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
