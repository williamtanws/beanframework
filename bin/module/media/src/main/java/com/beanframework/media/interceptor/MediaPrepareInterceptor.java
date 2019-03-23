package com.beanframework.media.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.media.domain.Media;

public class MediaPrepareInterceptor extends AbstractPrepareInterceptor<Media> {

	@Override
	public void onPrepare(Media model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

	}

}
