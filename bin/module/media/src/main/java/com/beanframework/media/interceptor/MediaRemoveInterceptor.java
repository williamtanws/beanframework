package com.beanframework.media.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.media.domain.Media;

public class MediaRemoveInterceptor implements RemoveInterceptor<Media> {

	@Override
	public void onRemove(Media model, InterceptorContext context) throws InterceptorException {
	}

}
