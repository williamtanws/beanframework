package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.ConverterException;

public class DtoAuditorConverter extends AbstractDtoConverter<Auditor, AuditorDto> implements DtoConverter<Auditor, AuditorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAuditorConverter.class);
	
	@Override
	public AuditorDto convert(Auditor source, DtoConverterContext context) throws ConverterException {
		return convert(source, new AuditorDto(), context);
	}

	public List<AuditorDto> convert(List<Auditor> sources, DtoConverterContext context) throws ConverterException {
		List<AuditorDto> convertedList = new ArrayList<AuditorDto>();
		for (Auditor source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private AuditorDto convert(Auditor source, AuditorDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertGeneric(source, prototype, context);

			prototype.setName(source.getName());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
