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
import com.beanframework.core.data.AddressDto;
import com.beanframework.user.domain.Address;

public class DtoAddressConverter extends AbstractDtoConverter<Address, AddressDto> implements DtoConverter<Address, AddressDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAddressConverter.class);

	@Override
	public AddressDto convert(Address source, DtoConverterContext context) throws ConverterException {
		try {
			AddressDto target = new AddressDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<AddressDto> convert(List<Address> sources, DtoConverterContext context) throws ConverterException {
		List<AddressDto> convertedList = new ArrayList<AddressDto>();
		try {
			for (Address source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
