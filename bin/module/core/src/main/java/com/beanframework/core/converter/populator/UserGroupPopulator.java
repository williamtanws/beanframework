package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class UserGroupPopulator extends AbstractPopulator<UserGroup, UserGroupDto> implements Populator<UserGroup, UserGroupDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupPopulator.class);

	@Override
	public void populate(UserGroup source, UserGroupDto target) throws PopulatorException {
		try {
			populateCommon(source, target);
			target.setName(source.getName());

			Hibernate.initialize(source.getUserAuthorities());
			for (UserAuthority userAuthority : source.getUserAuthorities()) {
				target.getUserAuthorities().add(populateUserAuthority(userAuthority));
			}

			Hibernate.initialize(source.getUserGroups());
			for (UserGroup userGroup : source.getUserGroups()) {
				target.getUserGroups().add(populateUserGroup(userGroup));
			}

			for (UserGroupField field : source.getFields()) {
				target.getFields().add(populateUserGroupField(field));
			}

			Collections.sort(target.getFields(), new Comparator<UserGroupFieldDto>() {
				@Override
				public int compare(UserGroupFieldDto o1, UserGroupFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public UserAuthorityDto populateUserAuthority(UserAuthority source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserAuthorityDto target = new UserAuthorityDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setEnabled(source.getEnabled());
			target.setUserPermission(populateUserPermission(source.getUserPermission()));
			target.setUserRight(populateUserRight(source.getUserRight()));

			return target;
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
			
			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public UserGroupFieldDto populateUserGroupField(UserGroupField source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserGroupFieldDto target = new UserGroupFieldDto();
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
