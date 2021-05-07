package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionAttributeDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightAttributeDto;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionAttribute;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightAttribute;

public class UserAuthorityPopulator extends AbstractPopulator<UserAuthority, UserAuthorityDto> implements Populator<UserAuthority, UserAuthorityDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityPopulator.class);

	@Override
	public void populate(UserAuthority source, UserAuthorityDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setEnabled(source.getEnabled());
			target.setUserPermission(populateUserPermission(source.getUserPermission()));
			target.setUserRight(populateUserRight(source.getUserRight()));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public UserPermissionDto populateUserPermission(UserPermission source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserPermissionDto target = new UserPermissionDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setName(source.getName());
			target.setSort(source.getSort());

			for (UserPermissionAttribute field : source.getAttributes()) {
				target.getAttributes().add(populateUserPermissionField(field));
			}

			if (target.getAttributes() != null)
				Collections.sort(target.getAttributes(), new Comparator<UserPermissionAttributeDto>() {
					@Override
					public int compare(UserPermissionAttributeDto o1, UserPermissionAttributeDto o2) {
						if (o1.getDynamicFieldSlot().getSort() == null)
							return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

						if (o2.getDynamicFieldSlot().getSort() == null)
							return -1;

						return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
					}
				});

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public UserPermissionAttributeDto populateUserPermissionField(UserPermissionAttribute source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserPermissionAttributeDto target = new UserPermissionAttributeDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setValue(source.getValue());
			target.setDynamicFieldSlot(populateDynamicFieldSlot(source.getDynamicFieldSlot()));

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public UserRightDto populateUserRight(UserRight source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserRightDto target = new UserRightDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setName(source.getName());
			target.setSort(source.getSort());

			for (UserRightAttribute field : source.getAttributes()) {
				target.getAttributes().add(populateUserRightField(field));
			}

			if (target.getAttributes() != null)
				Collections.sort(target.getAttributes(), new Comparator<UserRightAttributeDto>() {
					@Override
					public int compare(UserRightAttributeDto o1, UserRightAttributeDto o2) {
						if (o1.getDynamicFieldSlot().getSort() == null)
							return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

						if (o2.getDynamicFieldSlot().getSort() == null)
							return -1;

						return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
					}
				});

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public UserRightAttributeDto populateUserRightField(UserRightAttribute source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserRightAttributeDto target = new UserRightAttributeDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setValue(source.getValue());
			target.setDynamicFieldSlot(populateDynamicFieldSlot(source.getDynamicFieldSlot()));

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
