package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.MyAccountDto;
import com.beanframework.user.domain.User;

public class DtoMyAccountConverter extends AbstractDtoConverter<User, MyAccountDto> implements DtoConverter<User, MyAccountDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMyAccountConverter.class);

	@Override
	public MyAccountDto convert(User source) throws ConverterException {
		return super.convert(source, new MyAccountDto());
	}
}
