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
	}

}
