package com.beanframework.media.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.media.domain.Media;

public class MediaLoadInterceptor implements LoadInterceptor<Media> {

	@Override
	public void onLoad(Media model) throws InterceptorException {
	}

}
