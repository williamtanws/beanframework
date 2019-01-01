package com.beanframework.cronjob.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

		if (source.getId() != null)
			prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		prototype.setName(source.getName());
		prototype.setValue(source.getValue());
		
		return prototype;
	}

}
