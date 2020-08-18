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
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.Company;

@Component
public class CompanyFullPopulator extends AbstractPopulator<Company, CompanyDto> implements Populator<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CompanyFullPopulator.class);
	
	@Autowired
	private CompanyBasicPopulator companyBasicPopulator;
	
	@Autowired
	private CountryBasicPopulator countryBasicPopulator;
	
	@Autowired
	private AddressBasicPopulator addressBasicPopulator;
	
	@Autowired
	private UserBasicPopulator userBasicPopulator;

	@Override
	public void populate(Company source, CompanyDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
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

			target.setContactPerson(modelService.getDto(source.getContactPerson(), UserDto.class, new DtoConverterContext(userBasicPopulator)));
			target.setResponsibleCompany(modelService.getDto(source.getResponsibleCompany(), CompanyDto.class, new DtoConverterContext(companyBasicPopulator)));
			target.setCountry(modelService.getDto(source.getCountry(), CountryDto.class, new DtoConverterContext(countryBasicPopulator)));
			target.setAddresses(modelService.getDto(source.getAddresses(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setShippingAddress(modelService.getDto(source.getShippingAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setUnloadingAddress(modelService.getDto(source.getUnloadingAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setBillingAddress(modelService.getDto(source.getBillingAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setContactAddress(modelService.getDto(source.getContactAddress(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			target.setUsers(modelService.getDto(source.getUsers(), UserDto.class, new DtoConverterContext(userBasicPopulator)));
			
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
