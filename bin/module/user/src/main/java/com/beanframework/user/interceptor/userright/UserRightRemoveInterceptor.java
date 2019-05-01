package com.beanframework.user.interceptor.userright;

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
import com.beanframework.user.domain.UserRight;

public class UserRightRemoveInterceptor extends AbstractRemoveInterceptor<UserRight> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(UserRight model, InterceptorContext context) throws InterceptorException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.USER_AUTHORITIES + "." + UserAuthority.USER_RIGHT + "." + UserRight.UUID, model.getUuid());
			List<UserGroup> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, UserGroup.class);

			for (int i = 0; i < entities.size(); i++) {

				boolean removed = false;
				for (int j = 0; j < entities.get(i).getUserAuthorities().size(); j++) {
					if (entities.get(i).getUserAuthorities().get(j).getUserRight().getUuid().equals(model.getUuid())) {
						entities.get(i).getUserAuthorities().get(j).setUserRight(null);
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
