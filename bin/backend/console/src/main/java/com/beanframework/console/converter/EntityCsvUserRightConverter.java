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
import com.beanframework.console.csv.UserRightCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;
import com.beanframework.user.service.UserRightService;

@Component
public class EntityCsvUserRightConverter implements EntityConverter<UserRightCsv, UserRight> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvUserRightConverter.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserRightService userRightService;

	@Override
	public UserRight convert(UserRightCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.ID, source.getId());

				UserRight prototype = userRightService.findOneEntityByProperties(properties);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new UserRight());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public UserRight convert(UserRightCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private UserRight convert(UserRightCsv source, UserRight prototype) throws ConverterException {

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
							UserRightField field = new UserRightField();
							field.setId(prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId);
							field.setValue(StringUtils.stripToNull(value));
							field.setDynamicField(entityDynamicField);
							field.setUserRight(prototype);
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
