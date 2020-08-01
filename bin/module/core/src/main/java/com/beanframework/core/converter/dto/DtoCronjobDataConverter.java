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
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.cronjob.domain.CronjobData;

public class DtoCronjobDataConverter extends AbstractDtoConverter<CronjobData, CronjobDataDto> implements DtoConverter<CronjobData, CronjobDataDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCronjobDataConverter.class);

	@Override
	public CronjobDataDto convert(CronjobData source, DtoConverterContext context) throws ConverterException {
		try {
			CronjobDataDto target = new CronjobDataDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<CronjobDataDto> convert(List<CronjobData> sources, DtoConverterContext context) throws ConverterException {
		List<CronjobDataDto> convertedList = new ArrayList<CronjobDataDto>();
		for (CronjobData source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
