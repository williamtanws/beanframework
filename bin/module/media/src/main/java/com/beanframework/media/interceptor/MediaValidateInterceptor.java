package com.beanframework.media.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.media.domain.Media;

public class MediaValidateInterceptor extends AbstractValidateInterceptor<Media> {

	@Override
	public void onValidate(Media model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
