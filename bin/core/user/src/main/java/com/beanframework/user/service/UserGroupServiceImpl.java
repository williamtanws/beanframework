package com.beanframework.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserGroup;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private ModelService modelService;

	@Override
	public UserGroup create() throws Exception {
		return modelService.create(UserGroup.class);
	}

	@Override
	public List<UserGroup> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoByPropertiesAndSorts(null, sorts, null, null, UserGroup.class);
	}

	@Override
	public UserGroup saveEntity(UserGroup model) throws BusinessException {
		return (UserGroup) modelService.saveEntity(model, UserGroup.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.ID, id);
			UserGroup model = modelService.findOneEntityByProperties(properties, UserGroup.class);
			modelService.deleteByEntity(model, UserGroup.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
