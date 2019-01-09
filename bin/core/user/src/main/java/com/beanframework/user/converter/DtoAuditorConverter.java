package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.ConverterException;

public class DtoAuditorConverter implements DtoConverter<Auditor, Auditor> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAuditorConverter.class);

	@Override
	public Auditor convert(Auditor source) throws ConverterException {
		return convert(source, new Auditor());
	}

	public List<Auditor> convert(List<Auditor> sources) throws ConverterException {
		List<Auditor> convertedList = new ArrayList<Auditor>();
		for (Auditor source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Auditor convert(Auditor source, Auditor prototype) throws ConverterException {

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
