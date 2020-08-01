package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.company.domain.Company;
import com.beanframework.core.data.CompanyDto;

@Component
public class CompanyFullPopulator extends AbstractPopulator<Company, CompanyDto> implements Populator<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CompanyFullPopulator.class);

	@Override
	public void populate(Company source, CompanyDto target) throws PopulatorException {
		convertCommonProperties(source, target);
	}

}
