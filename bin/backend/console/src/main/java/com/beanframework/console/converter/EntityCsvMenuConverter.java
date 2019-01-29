package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.MenuCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;
import com.beanframework.menu.domain.MenuTargetTypeEnum;
import com.beanframework.user.domain.UserGroup;

@Component
public class EntityCsvMenuConverter implements EntityConverter<MenuCsv, Menu> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvMenuConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Menu convert(MenuCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Menu.ID, source.getId());

				Menu prototype = modelService.findOneEntityByProperties(properties, true, Menu.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(Menu.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Menu convert(MenuCsv source, Menu prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setSort(source.getSort());
			prototype.setIcon(StringUtils.stripToNull(source.getIcon()));
			prototype.setPath(StringUtils.stripToNull(source.getPath()));

			if (StringUtils.isBlank(source.getTarget())) {
				prototype.setTarget(MenuTargetTypeEnum.SELF);
			} else {
				prototype.setTarget(MenuTargetTypeEnum.valueOf(source.getTarget()));
			}
			prototype.setEnabled(source.isEnabled());

			// Parent
			if (StringUtils.isNotBlank(source.getParent())) {
				if (prototype.getParent() != null && StringUtils.equals(prototype.getParent().getId(), source.getParent()) == false) {

					Map<String, Object> parentProperties = new HashMap<String, Object>();
					parentProperties.put(Menu.ID, source.getParent());
					Menu parent = modelService.findOneEntityByProperties(parentProperties, true, Menu.class);

					if (parent == null) {
						LOGGER.error("Parent not exists: " + source.getParent());
					} else {
						Hibernate.initialize(parent.getChilds());

						boolean addChild = true;
						for (Menu child : parent.getChilds()) {
							if (child.getUuid().equals(prototype.getUuid())) {
								addChild = false;
							}
						}
						if (addChild) {
							parent.getChilds().add(prototype);
							prototype.setParent(parent);
						}
					}
				}
			}

			// Dynamic Field
			if (StringUtils.isNotBlank(source.getDynamicField())) {
				String[] dynamicFields = source.getDynamicField().split(ImportListener.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(ImportListener.EQUALS)[0];
					String value = dynamicField.split(ImportListener.EQUALS)[1];

					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (prototype.getFields().get(i).getId().equals(prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(value));
							add = false;
						}
					}

					if (add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, true, DynamicField.class);

						if (entityDynamicField == null) {
							LOGGER.error("DynamicField ID not exists: " + dynamicFieldId);
						} else {
							MenuField field = new MenuField();
							field.setId(prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId);
							field.setValue(StringUtils.stripToNull(value));
							field.setDynamicField(entityDynamicField);
							field.setMenu(prototype);
							prototype.getFields().add(field);
						}
					}
				}
			}

			// User Group
			if (StringUtils.isNotBlank(source.getUserGroupIds())) {
				String[] userGroupIds = source.getUserGroupIds().split(ImportListener.SPLITTER);
				for (int i = 0; i < userGroupIds.length; i++) {
					Map<String, Object> userGroupProperties = new HashMap<String, Object>();
					userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
					UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, true, UserGroup.class);

					if (userGroup == null) {
						LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
					} else {
						prototype.getUserGroups().add(userGroup);
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
