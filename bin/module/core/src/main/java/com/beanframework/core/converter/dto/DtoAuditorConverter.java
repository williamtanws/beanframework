package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.ConverterException;

public class DtoAuditorConverter extends AbstractDtoConverter<Auditor, AuditorDto> implements DtoConverter<Auditor, AuditorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAuditorConverter.class);

	@Override
	public AuditorDto convert(Auditor source) throws ConverterException {
		return super.convert(source, new AuditorDto());
	}
}
