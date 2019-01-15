package com.beanframework.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserPermission;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermission create() throws Exception {
		return modelService.create(UserPermission.class);
	}

	@Override
	public List<UserPermission> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findCachedEntityByPropertiesAndSorts(null, sorts, null, null, UserPermission.class);
	}

	@Override
	public UserPermission saveEntity(UserPermission model) throws BusinessException {
		return (UserPermission) modelService.saveEntity(model, UserPermission.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserPermission.ID, id);
			UserPermission model = modelService.findOneEntityByProperties(properties, UserPermission.class);
			modelService.deleteByEntity(model, UserPermission.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
