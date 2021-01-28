package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.enumuration.domain.Enumeration;

public class EntityEnumerationConverter implements EntityConverter<EnumerationDto, Enumeration> {

	@Autowired
	private ModelService modelService;

	@Override
	public Enumeration convert(EnumerationDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Enumeration.UUID, source.getUuid());
				Enumeration prototype = modelService.findOneByProperties(properties, Enumeration.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Enumeration.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Enumeration convertToEntity(EnumerationDto source, Enumeration prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getSort() == null) {
			if (prototype.getSort() != null) {
				prototype.setSort(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getSort() == null || prototype.getSort() == source.getSort() == Boolean.FALSE) {
				prototype.setSort(source.getSort());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		return prototype;
	}

}
