package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.EmailConstants;
import com.beanframework.email.domain.Email;

public class DtoEmailConverter extends AbstractDtoConverter<Email, EmailDto> implements DtoConverter<Email, EmailDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoEmailConverter.class);

	@Value(EmailConstants.EMAIL_ATTACHMENT_LOCATION)
	public String EMAIL_ATTACHMENT_LOCATION;

	@Override
	public EmailDto convert(Email source, DtoConverterContext context) throws ConverterException {
		try {
			EmailDto target = new EmailDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<EmailDto> convert(List<Email> sources, DtoConverterContext context) throws ConverterException {
		List<EmailDto> convertedList = new ArrayList<EmailDto>();
		for (Email source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
