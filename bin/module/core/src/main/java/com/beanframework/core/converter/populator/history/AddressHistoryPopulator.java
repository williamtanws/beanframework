package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.AddressDto;
import com.beanframework.user.domain.Address;

@Component
public class AddressHistoryPopulator extends AbstractPopulator<Address, AddressDto> implements Populator<Address, AddressDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AddressHistoryPopulator.class);

	@Override
	public void populate(Address source, AddressDto target) throws PopulatorException {
		convertCommonProperties(source, target);
	}

}
