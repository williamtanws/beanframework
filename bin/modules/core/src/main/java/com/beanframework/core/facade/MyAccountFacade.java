package com.beanframework.core.facade;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MyAccountDto;

public interface MyAccountFacade {

  MyAccountDto getCurrentUser() throws BusinessException;

  MyAccountDto save(MyAccountDto user) throws BusinessException;

}
