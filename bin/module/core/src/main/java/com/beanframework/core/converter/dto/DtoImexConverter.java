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
import com.beanframework.core.data.ImexDto;
import com.beanframework.imex.domain.Imex;

public class DtoImexConverter extends AbstractDtoConverter<Imex, ImexDto> implements DtoConverter<Imex, ImexDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoImexConverter.class);

	@Override
	public ImexDto convert(Imex source, DtoConverterContext context) throws ConverterException {
		try {
			ImexDto target = new ImexDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<ImexDto> convert(List<Imex> sources, DtoConverterContext context) throws ConverterException {
		List<ImexDto> convertedList = new ArrayList<ImexDto>();
		try {
			for (Imex source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
