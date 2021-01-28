package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.cronjob.domain.CronjobData;

public class EntityCronjobDataConverter implements EntityConverter<CronjobDataDto, CronjobData> {

	@Autowired
	private ModelService modelService;

	@Override
	public CronjobData convert(CronjobDataDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(CronjobData.UUID, source.getUuid());

				CronjobData prototype = modelService.findOneByProperties(properties, CronjobData.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(CronjobData.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private CronjobData convertToEntity(CronjobDataDto source, CronjobData prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getValue()), prototype.getValue()) == Boolean.FALSE) {
			prototype.setValue(StringUtils.stripToNull(source.getValue()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
