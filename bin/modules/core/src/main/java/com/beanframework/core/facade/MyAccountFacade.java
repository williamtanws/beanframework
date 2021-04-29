package com.beanframework.core.facade;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MyAccountDto;

public interface MyAccountFacade {

	MyAccountDto getCurrentUser() throws Exception;

	MyAccountDto update(MyAccountDto user) throws BusinessException;

}
