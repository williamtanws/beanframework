package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.backoffice.data.AuditorDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.ConverterException;

public class DtoAuditorConverter implements DtoConverter<Auditor, AuditorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAuditorConverter.class);

	@Override
	public AuditorDto convert(Auditor source) throws ConverterException {
		return convert(source, new AuditorDto());
	}

	public List<AuditorDto> convert(List<Auditor> sources) throws ConverterException {
		List<AuditorDto> convertedList = new ArrayList<AuditorDto>();
		for (Auditor source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private AuditorDto convert(Auditor source, AuditorDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());

		return prototype;
	}

}
