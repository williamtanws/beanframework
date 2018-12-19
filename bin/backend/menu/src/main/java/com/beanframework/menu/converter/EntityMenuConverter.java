package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.converter.EntityUserGroupConverter;

public class EntityMenuConverter implements EntityConverter<Menu, Menu> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EntityUserGroupConverter entityUserGroupConverter;

	@Autowired
	private EntityMenuFieldConverter entityMenuFieldConverter;

	@Override
	public Menu convert(Menu source) throws ConverterException {

		Menu prototype;
		try {

			if (source.getUuid() != null) {

				Menu exists = modelService.findOneEntityByUuid(source.getUuid(), Menu.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Menu.class);
				}
			} else {
				prototype = modelService.create(Menu.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	public List<Menu> convert(List<Menu> sources) throws ConverterException {
		List<Menu> convertedList = new ArrayList<Menu>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Menu convert(Menu source, Menu prototype) throws ConverterException {

		if (source.getId() != null) {
			prototype.setId(source.getId());
		}
		prototype.setLastModifiedDate(new Date());

		prototype.setSort(source.getSort());
		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.getEnabled());

		if (source.getParent() != null) {
			prototype.setParent(this.convert(source.getParent()));
		}
		if (source.getChilds() != null) {
			prototype.setChilds(this.convert(source.getChilds()));
		}
		if (source.getUserGroups() != null) {
			prototype.setUserGroups(entityUserGroupConverter.convert(source.getUserGroups()));
		}
		if (source.getMenuFields() != null) {
			prototype.setMenuFields(entityMenuFieldConverter.convert(source.getMenuFields()));
		}

//		if (source.getParent() != null) {
//			if (source.getParent().getUuid() != null) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Menu.UUID, source.getParent().getUuid());
//				
//				Menu parent = modelService.findOneEntityByProperties(properties, Menu.class);
//				
//				prototype.setParent(parent == null ? null : parent);
//			} else if (StringUtils.isNotEmpty(source.getParent().getId())) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Menu.ID, source.getParent().getId());
//				
//				Menu parent = modelService.findOneEntityByProperties(properties, Menu.class);
//				
//				prototype.setParent(parent == null ? null : parent);
//			}
//		} else {
//			prototype.setParent(null);
//		}
//
//		prototype.getMenuFields().clear();
//		for (MenuField menuLang : source.getMenuFields()) {
//			if (menuLang.getLanguage().getUuid() != null) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.UUID, menuLang.getLanguage().getUuid());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					menuLang.setLanguage(language);
//					menuLang.setMenu(prototype);
//					prototype.getMenuFields().add(menuLang);
//				}
//			} else if (StringUtils.isNotEmpty(menuLang.getLanguage().getId())) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(Language.ID, menuLang.getLanguage().getId());
//				
//				Language language = modelService.findOneEntityByProperties(properties, Language.class);
//				
//				if (language != null) {
//					menuLang.setLanguage(language);
//					menuLang.setMenu(prototype);
//					prototype.getMenuFields().add(menuLang);
//				}
//			}
//		}
//
//		Hibernate.initialize(prototype.getUserGroups());
//		prototype.getUserGroups().clear();
//		for (UserGroup userGroup : source.getUserGroups()) {
//			if(userGroup.getUuid() != null) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(UserGroup.UUID, source.getUuid());
//				UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
//				
//				if (existingUserGroup != null) {
//					prototype.getUserGroups().add(existingUserGroup);
//				}
//			}
//			else if(StringUtils.isNotEmpty(userGroup.getId())) {
//				
//				Map<String, Object> properties = new HashMap<String, Object>();
//				properties.put(UserGroup.ID, source.getId());
//				UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
//				
//				if (existingUserGroup != null) {
//					prototype.getUserGroups().add(existingUserGroup);
//				}
//			}
//		}

		return prototype;
	}

}
