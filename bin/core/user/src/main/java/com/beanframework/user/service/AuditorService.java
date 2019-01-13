package com.beanframework.user.service;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.User;

public interface AuditorService {

	public Auditor create() throws Exception;

	Auditor saveDto(User model) throws BusinessException;

}
