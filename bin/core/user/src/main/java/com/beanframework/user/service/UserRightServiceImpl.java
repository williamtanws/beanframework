package com.beanframework.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private ModelService modelService;

	@Transactional
	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.delete(uuid, UserRight.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
