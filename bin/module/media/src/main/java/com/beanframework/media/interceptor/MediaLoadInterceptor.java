package com.beanframework.media.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.media.domain.Media;

public class MediaLoadInterceptor extends AbstractLoadInterceptor<Media> {

	@Override
	public void onLoad(Media model, InterceptorContext context) throws InterceptorException {
		super.onLoad(model, context);
	}

}
