package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class EntityUserRightConverter implements EntityConverter<UserRight, UserRight> {

	@Autowired
	private ModelService modelService;

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

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			prototype.setLastModifiedDate(new Date());

			prototype.setSort(source.getSort());
			if (source.getUserRightFields() != null && source.getUserRightFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getUserRightFields().size(); i++) {
					for (UserRightField sourceUserRightField : source.getUserRightFields()) {
						if (prototype.getUserRightFields().get(i).getUuid().equals(sourceUserRightField.getUuid())) {
							prototype.getUserRightFields().get(i).setValue(sourceUserRightField.getValue());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
