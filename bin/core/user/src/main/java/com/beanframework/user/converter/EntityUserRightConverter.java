package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.UUID, source.getUuid());

				UserRight prototype = modelService.findOneEntityByProperties(properties, UserRight.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(UserRight.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private UserRight convert(UserRight source, UserRight prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(StringUtils.strip(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.strip(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.strip(source.getName()), prototype.getName()) == false) {
				prototype.setName(StringUtils.strip(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getSort() != prototype.getSort()) {
				prototype.setSort(source.getSort());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserRightField sourceField : source.getFields()) {
						if (prototype.getFields().get(i).getUuid().equals(sourceField.getUuid())) {
							if (StringUtils.equals(StringUtils.strip(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
								prototype.getFields().get(i).setValue(StringUtils.strip(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
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
