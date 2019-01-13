package com.beanframework.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private ModelService modelService;

	@Override
	public UserRight create() throws Exception {
		return modelService.create(UserRight.class);
	}

	@Override
	public List<UserRight> findDtoBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findDtoByPropertiesAndSorts(null, sorts, null, null, UserRight.class);
	}

	@Override
	public UserRight saveEntity(UserRight model) throws BusinessException {
		return (UserRight) modelService.saveEntity(model, UserRight.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserRight.ID, id);
			UserRight model = modelService.findOneEntityByProperties(properties, UserRight.class);
			modelService.deleteByEntity(model, UserRight.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
