package com.beanframework.user.interceptor.usergroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

public class UserGroupRemoveInterceptor extends AbstractRemoveInterceptor<UserGroup> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(UserGroup model, InterceptorContext context) throws InterceptorException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(User.USER_GROUPS + "." + UserGroup.UUID, model.getUuid());
			List<User> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, User.class);

			for (int i = 0; i < entities.size(); i++) {

				boolean removed = false;
				for (int j = 0; j < entities.get(i).getUserGroups().size(); j++) {
					if (entities.get(i).getUserGroups().get(j).getUuid().equals(model.getUuid())) {
						entities.get(i).getUserGroups().remove(j);
						removed = true;
						break;
					}
				}

				if (removed)
					modelService.saveEntity(entities.get(i), User.class);
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}

}
