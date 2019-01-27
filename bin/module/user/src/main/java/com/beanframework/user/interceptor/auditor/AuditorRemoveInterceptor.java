package com.beanframework.user.interceptor.auditor;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;

public class AuditorRemoveInterceptor implements RemoveInterceptor<Auditor> {

	@Override
	public void onRemove(Auditor model) throws InterceptorException {
	}

}
