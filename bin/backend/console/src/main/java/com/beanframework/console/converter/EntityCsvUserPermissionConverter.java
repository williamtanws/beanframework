package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.UserPermissionCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

@Component
public class EntityCsvUserPermissionConverter implements EntityConverter<UserPermissionCsv, UserPermission> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvUserPermissionConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission convert(UserPermissionCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.ID, source.getId());

				UserPermission prototype = modelService.findOneEntityByProperties(properties, UserPermission.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new UserPermission());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public UserPermission convert(UserPermissionCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private UserPermission convert(UserPermissionCsv source, UserPermission prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setSort(Integer.valueOf(source.getSort()));

			// Dynamic Field
			if (StringUtils.isNotBlank(source.getDynamicFieldSlotIds())) {
				String[] dynamicFields = source.getDynamicFieldSlotIds().split(ImportListener.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(ImportListener.EQUALS)[0];
					String value = dynamicField.split(ImportListener.EQUALS)[1];

					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (StringUtils.equals(prototype.getFields().get(i).getId(), prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(value));
							add = false;
						}
					}

					if (add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, DynamicField.class);

						if (entityDynamicField == null) {
							LOGGER.error("DynamicField ID not exists: " + dynamicFieldId);
						} else {
							UserPermissionField field = new UserPermissionField();
							field.setId(prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId);
							field.setValue(StringUtils.stripToNull(value));
							field.setDynamicField(entityDynamicField);
							field.setUserPermission(prototype);
							prototype.getFields().add(field);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
