package com.beanframework.core.converter.populator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;

public class CompanyPopulator extends AbstractPopulator<Company, CompanyDto> implements Populator<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CompanyPopulator.class);

	@Override
	public void populate(Company source, CompanyDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setDescription(source.getDescription());
			target.setLineOfBusiness(source.getLineOfBusiness());
			target.setBuyer(source.getBuyer());
			target.setManufacturer(source.getManufacturer());
			target.setSupplier(source.getSupplier());
			target.setCarrier(source.getCarrier());
			target.setVatId(source.getVatId());
			target.setDunsId(source.getDunsId());
			target.setIlnId(source.getIlnId());
			target.setBuyerSpecificId(source.getBuyerSpecificId());
			target.setSupplierSpecificId(source.getSupplierSpecificId());

			target.setContactPerson(populateUser(source.getContactPerson()));
			target.setResponsibleCompany(populateCompany(source.getResponsibleCompany()));
			target.setCountry(populateCountry(source.getCountry()));
						
			for (UUID uuid : source.getAddresses()) {
				target.getAddresses().add(populateAddress(uuid));
			}
			
			target.setShippingAddress(populateAddress(source.getShippingAddress()));
			target.setUnloadingAddress(populateAddress(source.getUnloadingAddress()));
			target.setBillingAddress(populateAddress(source.getBillingAddress()));
			target.setContactAddress(populateAddress(source.getContactAddress()));

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public CompanyDto populateCompany(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			Company source = modelService.findOneByUuid(uuid, Company.class);
			CompanyDto target = new CompanyDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setName(source.getName());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
	
	public CountryDto populateCountry(UUID uuid) throws PopulatorException {
		if(uuid == null)
			return null;
		
		try {
			Country source = modelService.findOneByUuid(uuid, Country.class);
			CountryDto target = new CountryDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setName(source.getName());
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
			AddressDto target = new AddressDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));
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
	
	public RegionDto populateRegion(UUID uuid) throws PopulatorException {
		if(uuid == null)
			return null;
		
		try {
			Region source = modelService.findOneByUuid(uuid, Region.class);
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

}
