package com.beanframework.core.interceptor.media;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.media.domain.Media;

public class MediaRemoveInterceptor extends AbstractRemoveInterceptor<Media> {

	@Override
	public void onRemove(Media model, InterceptorContext context) throws InterceptorException {
	}

}
