package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

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

			for (UserPermissionField field : source.getFields()) {
				target.getFields().add(populateUserPermissionField(field));
			}

			if (target.getFields() != null)
				Collections.sort(target.getFields(), new Comparator<UserPermissionFieldDto>() {
					@Override
					public int compare(UserPermissionFieldDto o1, UserPermissionFieldDto o2) {
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

	public UserPermissionFieldDto populateUserPermissionField(UserPermissionField source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserPermissionFieldDto target = new UserPermissionFieldDto();
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

			for (UserRightField field : source.getFields()) {
				target.getFields().add(populateUserRightField(field));
			}

			if (target.getFields() != null)
				Collections.sort(target.getFields(), new Comparator<UserRightFieldDto>() {
					@Override
					public int compare(UserRightFieldDto o1, UserRightFieldDto o2) {
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

	public UserRightFieldDto populateUserRightField(UserRightField source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserRightFieldDto target = new UserRightFieldDto();
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
