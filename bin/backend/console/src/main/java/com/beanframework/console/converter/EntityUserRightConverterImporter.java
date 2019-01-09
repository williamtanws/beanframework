package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.UserRightCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

@Component
public class EntityUserRightConverterImporter implements EntityConverter<UserRightCsv, UserRight> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityUserRightConverterImporter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserRight convert(UserRightCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserRight.ID, source.getId());

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

	private UserRight convert(UserRightCsv source, UserRight prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.strip(source.getId()));
			prototype.setName(StringUtils.strip(source.getName()));
			prototype.setSort(source.getSort());

			// Dynamic Field
			if (source.getDynamicField() != null) {
				String[] dynamicFields = source.getDynamicField().split(Importer.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(Importer.EQUALS)[0];
					String value = dynamicField.split(Importer.EQUALS)[1];
					
					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (prototype.getFields().get(i).getId().equals(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(StringUtils.strip(value));
							add = false;
						}
					}
					
					if(add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, DynamicField.class);
						
						if(entityDynamicField != null) {
							UserRightField field = new UserRightField();
							field.setId(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId);
							field.setValue(StringUtils.strip(value));
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
