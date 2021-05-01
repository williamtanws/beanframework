package com.beanframework.user.service;

import com.beanframework.user.domain.UserRight;

public interface UserRightService {

	void generateUserRightFieldsOnInitialDefault(UserRight model) throws Exception;

	void generateUserRightFieldOnLoad(UserRight model) throws Exception;

}
