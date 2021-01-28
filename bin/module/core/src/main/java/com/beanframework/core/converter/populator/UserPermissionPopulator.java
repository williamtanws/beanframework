package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;


public class UserPermissionPopulator extends AbstractPopulator<UserPermission, UserPermissionDto> implements Populator<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionPopulator.class);

	@Override
	public void populate(UserPermission source, UserPermissionDto target) throws PopulatorException {
		try {
			populateCommon(source, target);
			target.setName(source.getName());
			target.setSort(source.getSort());

			for (UserPermissionField field : source.getFields()) {
				target.getFields().add(populateUserPermissionField(field));
			}

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
}
