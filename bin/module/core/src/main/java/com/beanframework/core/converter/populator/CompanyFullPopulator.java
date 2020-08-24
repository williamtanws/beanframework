package com.beanframework.core.converter.populator;

import java.util.UUID;

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
import com.beanframework.internationalization.domain.Country;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;

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

			if (source.getContactPerson() != null) {
				User entity = modelService.findOneByUuid(source.getContactPerson(), User.class);
				target.setContactPerson(modelService.getDto(entity, UserDto.class, new DtoConverterContext(userBasicPopulator)));
			}
			if (source.getResponsibleCompany() != null) {
				Company entity = modelService.findOneByUuid(source.getResponsibleCompany(), Company.class);
				target.setResponsibleCompany(modelService.getDto(entity, CompanyDto.class, new DtoConverterContext(companyBasicPopulator)));
			}
			if (source.getCountry() != null) {
				Country entity = modelService.findOneByUuid(source.getCountry(), Country.class);
				target.setCountry(modelService.getDto(entity, CountryDto.class, new DtoConverterContext(countryBasicPopulator)));
			}
			if (source.getAddresses() != null && source.getAddresses().isEmpty() == false) {
				for (UUID uuid : source.getAddresses()) {
					Address entity = modelService.findOneByUuid(uuid, Address.class);
					target.getAddresses().add(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
				}
			}
			if (source.getShippingAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getShippingAddress(), Address.class);
				target.setShippingAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getUnloadingAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getUnloadingAddress(), Address.class);
				target.setUnloadingAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getBillingAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getBillingAddress(), Address.class);
				target.setBillingAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getContactAddress() != null) {
				Address entity = modelService.findOneByUuid(source.getContactAddress(), Address.class);
				target.setContactAddress(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
			}
			if (source.getUsers() != null && source.getUsers().isEmpty() == false) {
				for (UUID uuid : source.getUsers()) {
					User entity = modelService.findOneByUuid(uuid, User.class);
					target.getUsers().add(modelService.getDto(entity, UserDto.class, new DtoConverterContext(userBasicPopulator)));
				}
			}

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
