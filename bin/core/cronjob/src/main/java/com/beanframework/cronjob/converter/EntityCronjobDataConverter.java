package com.beanframework.cronjob.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.CronjobData;

public class EntityCronjobDataConverter implements EntityConverter<CronjobData, CronjobData> {

	@Autowired
	private ModelService modelService;

	@Override
	public CronjobData convert(CronjobData source) throws ConverterException {

		CronjobData prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(CronjobData.UUID, source.getUuid());

				CronjobData exists = modelService.findOneEntityByProperties(properties, CronjobData.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(CronjobData.class);
				}
			} else {
				prototype = modelService.create(CronjobData.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private CronjobData convert(CronjobData source, CronjobData prototype) {

		prototype.setLastModifiedDate(new Date());

		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
			prototype.setId(StringUtils.strip(source.getId()));

		if (StringUtils.equals(source.getName(), prototype.getName()) == false)
			prototype.setName(StringUtils.strip(source.getName()));

		if (StringUtils.equals(source.getValue(), prototype.getValue()) == false)
			prototype.setValue(StringUtils.strip(source.getValue()));

		return prototype;
	}

}
