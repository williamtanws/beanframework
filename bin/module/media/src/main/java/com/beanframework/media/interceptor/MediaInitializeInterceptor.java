package com.beanframework.media.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.media.domain.Media;

public class MediaInitializeInterceptor implements InitializeInterceptor<Media> {

	@Override
	public void onInitialize(Media model) throws InterceptorException {
	}

}
