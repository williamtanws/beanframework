package com.beanframework.media.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.media.domain.Media;

public class MediaInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Media> {

	@Override
	public void onInitialDefaults(Media model) throws InterceptorException {
	}

}
