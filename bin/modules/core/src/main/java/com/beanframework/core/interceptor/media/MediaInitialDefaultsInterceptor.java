package com.beanframework.core.interceptor.media;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.media.domain.Media;

public class MediaInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Media> {

	@Override
	public void onInitialDefaults(Media model, InterceptorContext context) throws InterceptorException {
	}

}
