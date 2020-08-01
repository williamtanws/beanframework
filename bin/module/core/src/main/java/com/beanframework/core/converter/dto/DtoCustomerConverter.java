package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.customer.domain.Customer;

public class DtoCustomerConverter extends AbstractDtoConverter<Customer, CustomerDto> implements DtoConverter<Customer, CustomerDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCustomerConverter.class);

	@Override
	public CustomerDto convert(Customer source, DtoConverterContext context) throws ConverterException {
		try {
			CustomerDto target = new CustomerDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<CustomerDto> convert(List<Customer> sources, DtoConverterContext context) throws ConverterException {
		List<CustomerDto> convertedList = new ArrayList<CustomerDto>();
		for (Customer source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
