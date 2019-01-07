package com.beanframework.user.service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.User;

public interface AuditorFacade {

	public void save(User user) throws BusinessException;
}
