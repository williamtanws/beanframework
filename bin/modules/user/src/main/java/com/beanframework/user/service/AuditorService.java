package com.beanframework.user.service;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.User;

public interface AuditorService {

  Auditor saveEntityByUser(User model) throws BusinessException;
}
