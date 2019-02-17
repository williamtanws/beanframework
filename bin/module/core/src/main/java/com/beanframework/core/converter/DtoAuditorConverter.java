package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;

public class DtoAuditorConverter implements DtoConverter<Auditor, AuditorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAuditorConverter.class);

	@Autowired
	private ModelService modelService;

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

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());

		try {
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
