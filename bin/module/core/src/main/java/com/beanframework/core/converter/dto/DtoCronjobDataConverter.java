package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.cronjob.domain.CronjobData;

public class DtoCronjobDataConverter extends AbstractDtoConverter<CronjobData, CronjobDataDto> implements DtoConverter<CronjobData, CronjobDataDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCronjobDataConverter.class);

	@Override
	public CronjobDataDto convert(CronjobData source) throws ConverterException {
		return super.convert(source, new CronjobDataDto());
	}

}
