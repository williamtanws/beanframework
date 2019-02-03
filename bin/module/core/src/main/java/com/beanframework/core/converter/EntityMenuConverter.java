package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

public class EntityMenuConverter implements EntityConverter<MenuDto, Menu> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityMenuConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Menu convert(MenuDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {

				Menu prototype = modelService.findOneEntityByUuid(source.getUuid(), true, Menu.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Menu.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	public List<Menu> convert(List<MenuDto> sources) throws ConverterException {
		List<Menu> convertedList = new ArrayList<Menu>();
		for (MenuDto source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Menu convert(MenuDto source, Menu prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.isBlank(source.getSort())) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort().equals(Integer.valueOf(source.getSort())) == false) {
					prototype.setSort(Integer.valueOf(source.getSort()));
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getIcon()), prototype.getIcon()) == false) {
				prototype.setIcon(StringUtils.stripToNull(source.getIcon()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getPath()), prototype.getPath()) == false) {
				prototype.setPath(StringUtils.stripToNull(source.getPath()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getTarget() == source.getTarget() == false) {
				prototype.setTarget(source.getTarget());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getEnabled() == null) {
				if (prototype.getEnabled() != null) {
					prototype.setEnabled(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getEnabled() == null || prototype.getEnabled().equals(source.getEnabled()) == false) {
					prototype.setEnabled(source.getEnabled());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (MenuFieldDto sourceField : source.getFields()) {

						if (prototype.getFields().get(i).getDynamicField().getUuid().equals(sourceField.getDynamicField().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// UserGroup
			if (source.getTableUserGroups() != null) {

				for (int i = 0; i < source.getTableUserGroups().length; i++) {

					boolean remove = true;
					if (source.getTableSelectedUserGroups() != null && source.getTableSelectedUserGroups().length > i && BooleanUtils.parseBoolean(source.getTableSelectedUserGroups()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(source.getTableUserGroups()[i]))) {
								userGroupsIterator.remove();
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					} else {
						boolean add = true;
						for (Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(source.getTableUserGroups()[i]))) {
								add = false;
							}
						}

						if (add) {
							UserGroup entityUserGroups = modelService.findOneEntityByUuid(UUID.fromString(source.getTableUserGroups()[i]), false, UserGroup.class);
							prototype.getUserGroups().add(entityUserGroups);
							prototype.setLastModifiedDate(lastModifiedDate);
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
