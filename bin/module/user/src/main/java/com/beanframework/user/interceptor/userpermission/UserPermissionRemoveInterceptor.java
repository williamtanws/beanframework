package com.beanframework.user.interceptor.userpermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionRemoveInterceptor extends AbstractRemoveInterceptor<UserPermission> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(UserPermission model, InterceptorContext context) throws InterceptorException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.USER_AUTHORITIES + "." + UserAuthority.USER_PERMISSION + "." + UserPermission.UUID, model.getUuid());
			List<UserGroup> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, UserGroup.class);

			for (int i = 0; i < entities.size(); i++) {

				boolean removed = false;
				for (int j = 0; j < entities.get(i).getUserAuthorities().size(); j++) {
					if (entities.get(i).getUserAuthorities().get(j).getUserPermission().getUuid().equals(model.getUuid())) {
						entities.get(i).getUserAuthorities().get(j).setUserPermission(null);
						removed = true;
						break;
					}
				}

				if (removed)
					modelService.saveEntity(entities.get(i), UserGroup.class);
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
