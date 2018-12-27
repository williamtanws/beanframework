package com.beanframework.user.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class UserGroupPrepareInterceptor implements PrepareInterceptor<UserGroup> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public void onPrepare(UserGroup model) throws InterceptorException {
		if (model.getUserAuthorities() != null) {
			for (int i = 0; i < model.getUserAuthorities().size(); i++) {
				if (model.getUserAuthorities().get(i).getEnabled() == null) {
					model.getUserAuthorities().get(i).setEnabled(Boolean.FALSE);
				}
			}
		}
		
		generateUserGroupField(model);
		generateUserAuthority(model);
		
		Iterator<UserGroup> userGroups = model.getUserGroups().iterator();
		while(userGroups.hasNext()) {
			if(userGroups.next().getUuid().equals(model.getUuid())) {
				userGroups.remove();
			}
		}
		
		for (int i = 0; i < model.getUserGroupFields().size(); i++) {
			if (StringUtils.isBlank(model.getUserGroupFields().get(i).getValue())) {
				model.getUserGroupFields().get(i).setValue(null);
			}
		}
	}
	
	private void generateUserGroupField(UserGroup model) throws InterceptorException {
		try {
			Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
			dynamicFieldProperties.put(DynamicField.FIELD_GROUP, UserGroup.class.getSimpleName());
			List<DynamicField> dynamicFields = modelService.findEntityByProperties(dynamicFieldProperties, DynamicField.class);

			for (DynamicField dynamicField : dynamicFields) {

				boolean add = true;
				for (UserGroupField userGroupField : model.getUserGroupFields()) {
					if (dynamicField.getUuid().equals(userGroupField.getDynamicField().getUuid())) {
						add = false;
					}
				}

				if (add) {
					UserGroupField userGroupField = modelService.create(UserGroupField.class);
					userGroupField.setDynamicField(dynamicField);
					userGroupField.setId(model.getId() + "_" + dynamicField.getId());

					userGroupField.setUserGroup(model);
					model.getUserGroupFields().add(userGroupField);
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

	private void generateUserAuthority(UserGroup model) throws InterceptorException {
		try {
			Hibernate.initialize(model.getUserAuthorities());

			Map<String, Sort.Direction> userPermissionSorts = new HashMap<String, Sort.Direction>();
			userPermissionSorts.put(UserPermission.SORT, Sort.Direction.ASC);
			List<UserPermission> userPermissions = modelService.findEntityBySorts(userPermissionSorts, UserPermission.class);

			Map<String, Sort.Direction> userRightSorts = new HashMap<String, Sort.Direction>();
			userRightSorts.put(UserRight.SORT, Sort.Direction.ASC);
			List<UserRight> userRights = modelService.findEntityBySorts(userRightSorts, UserRight.class);

			for (UserPermission userPermission : userPermissions) {
				for (UserRight userRight : userRights) {

					String authorityId = model.getId() + "_" + userPermission.getId() + "_" + userRight.getId();

					boolean add = true;
					if (model.getUserAuthorities() != null && model.getUserAuthorities().isEmpty() == false) {
						for (UserAuthority userAuthority : model.getUserAuthorities()) {
							if (userAuthority.getId().equals(authorityId)) {
								add = false;
							}
						}
					}

					if (add) {
						UserAuthority userAuthority = modelService.create(UserAuthority.class);
						userAuthority.setId(authorityId);
						userAuthority.setUserPermission(userPermission);
						userAuthority.setUserRight(userRight);

						userAuthority.setUserGroup(model);
						model.getUserAuthorities().add(userAuthority);
					}
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
