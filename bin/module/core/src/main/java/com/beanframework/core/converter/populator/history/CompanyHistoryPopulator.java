package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.user.domain.Company;

@Component
public class CompanyHistoryPopulator extends AbstractPopulator<Company, CompanyDto> implements Populator<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CompanyHistoryPopulator.class);

	@Override
	public void populate(Company source, CompanyDto target) throws PopulatorException {
		convertCommonProperties(source, target);
	}

}
