package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;

public class EntityUserRightConverter implements EntityConverter<UserRight, UserRight> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EntityUserRightFieldConverter entityUserRightFieldConverter;

	@Override
	public UserRight convert(UserRight source) throws ConverterException {

		UserRight prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.UUID, source.getUuid());

				UserRight exists = modelService.findOneEntityByProperties(properties, UserRight.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(UserRight.class);
				}
			} else {
				prototype = modelService.create(UserRight.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private UserRight convert(UserRight source, UserRight prototype) throws ConverterException {

		if (source.getId() != null)
			prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		if (source.getSort() != null)
			prototype.setSort(source.getSort());
		if (source.getUserRightFields() != null)
			prototype.setUserRightFields(entityUserRightFieldConverter.convert(source.getUserRightFields()));

		return prototype;
	}

}
