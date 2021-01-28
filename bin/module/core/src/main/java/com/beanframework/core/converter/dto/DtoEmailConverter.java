package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.domain.Email;

public class DtoEmailConverter extends AbstractDtoConverter<Email, EmailDto> implements DtoConverter<Email, EmailDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEmailConverter.class);

	@Override
	public EmailDto convert(Email source) throws ConverterException {
		return super.convert(source, new EmailDto());
	}

}
