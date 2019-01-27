package com.beanframework.backoffice.converter;

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

				CronjobData prototype = modelService.findOneEntityByProperties(properties, true,CronjobData.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(CronjobData.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private CronjobData convert(CronjobDataDto source, CronjobData prototype) {

		Date lastModifiedDate = new Date();
		
		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}
		
		if (StringUtils.equals(StringUtils.stripToNull(source.getValue()), prototype.getValue()) == false) {
			prototype.setValue(StringUtils.stripToNull(source.getValue()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
