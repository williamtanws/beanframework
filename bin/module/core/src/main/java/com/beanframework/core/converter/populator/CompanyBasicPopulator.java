package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.user.domain.Company;

@Component
public class CompanyBasicPopulator extends AbstractPopulator<Company, CompanyDto> implements Populator<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CompanyBasicPopulator.class);

	@Override
	public void populate(Company source, CompanyDto target) throws PopulatorException {
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
	}

}
