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
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

public class DtoCronjobConverter extends AbstractDtoConverter<Cronjob, CronjobDto> implements DtoConverter<Cronjob, CronjobDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCronjobConverter.class);

	@Override
	public CronjobDto convert(Cronjob source, DtoConverterContext context) throws ConverterException {
		try {
			CronjobDto target = new CronjobDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<CronjobDto> convert(List<Cronjob> sources, DtoConverterContext context) throws ConverterException {
		List<CronjobDto> convertedList = new ArrayList<CronjobDto>();
		try {
			for (Cronjob source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
