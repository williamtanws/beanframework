package com.beanframework.console.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.UserPermissionCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.user.domain.UserPermission;

@Component
public class EntityUserPermissionImporterConverter implements EntityConverter<UserPermissionCsv, UserPermission> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityUserPermissionImporterConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission convert(UserPermissionCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserPermission.ID, source.getId());

				UserPermission prototype = modelService.findOneEntityByProperties(properties, UserPermission.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(UserPermission.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private UserPermission convert(UserPermissionCsv source, UserPermission prototype) throws ConverterException {

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			prototype.setLastModifiedDate(new Date());

			prototype.setSort(source.getSort());

			// Dynamic Field
			if (source.getDynamicField() != null) {
				String[] dynamicFields = source.getDynamicField().split(Importer.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(Importer.EQUALS)[0];
					String value = dynamicField.split(Importer.EQUALS)[1];
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (prototype.getFields().get(i).getId().equals(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(value);
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
