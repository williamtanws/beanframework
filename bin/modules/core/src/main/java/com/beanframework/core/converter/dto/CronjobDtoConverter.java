package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobDtoConverter extends AbstractDtoConverter<Cronjob, CronjobDto> implements DtoConverter<Cronjob, CronjobDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CronjobDtoConverter.class);

	@Override
	public CronjobDto convert(Cronjob source) throws ConverterException {
		return super.convert(source, new CronjobDto());
	}

}
