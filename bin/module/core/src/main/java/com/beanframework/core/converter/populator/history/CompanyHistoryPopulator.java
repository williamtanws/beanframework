package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.converter.populator.AddressBasicPopulator;
import com.beanframework.core.converter.populator.CompanyBasicPopulator;
import com.beanframework.core.converter.populator.CountryBasicPopulator;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.data.CountryDto;
import com.beanframework.user.domain.Company;

@Component
public class CompanyHistoryPopulator extends AbstractPopulator<Company, CompanyDto> implements Populator<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CompanyHistoryPopulator.class);

	@Autowired
	private CompanyBasicPopulator companyBasicPopulator;
	
	@Autowired
	private CountryBasicPopulator countryBasicPopulator;
	
	@Autowired
	private AddressBasicPopulator addressBasicPopulator;

	@Override
	public void populate(Company source, CompanyDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(target.getName());
			target.setDescription(target.getDescription());
			target.setLineOfBusiness(target.getLineOfBusiness());
			target.setBuyer(target.getBuyer());
			target.setManufacturer(target.getManufacturer());
			target.setSupplier(target.getSupplier());
			target.setCarrier(target.getCarrier());
			target.setVatId(target.getVatId());
			target.setDunsId(target.getDunsId());
			target.setIlnId(target.getIlnId());
			target.setBuyerSpecificId(target.getBuyerSpecificId());
			target.setSupplierSpecificId(target.getSupplierSpecificId());
			
			target.setResponsibleCompany(modelService.getDto(source.getResponsibleCompany(), CompanyDto.class, new DtoConverterContext(companyBasicPopulator)));
			target.setCountry(modelService.getDto(source.getCountry(), CountryDto.class, new DtoConverterContext(countryBasicPopulator)));
			target.setAddresses(modelService.getDto(source.getAddresses(), CountryDto.class, new DtoConverterContext(addressBasicPopulator)));
			
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
