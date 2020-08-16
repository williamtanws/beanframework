package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.user.domain.Customer;

@Component
public class CustomerBasicPopulator extends AbstractPopulator<Customer, CustomerDto> implements Populator<Customer, CustomerDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CustomerBasicPopulator.class);

	@Override
	public void populate(Customer source, CustomerDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
