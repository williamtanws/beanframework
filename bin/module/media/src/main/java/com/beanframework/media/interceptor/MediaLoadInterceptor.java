package com.beanframework.media.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.media.domain.Media;

public class MediaLoadInterceptor extends AbstractLoadInterceptor<Media> {

	@Override
	public void onLoad(Media model, InterceptorContext context) throws InterceptorException {

		Media prototype = new Media();
		loadCommonProperties(model, prototype, context);
		prototype.setFileName(model.getFileName());
		prototype.setFileType(model.getFileType());
		prototype.setFileSize(model.getFileSize());
		prototype.setTitle(model.getTitle());
		prototype.setCaption(model.getCaption());
		prototype.setAltText(model.getAltText());
		prototype.setDescription(model.getDescription());
		prototype.setUrl(model.getUrl());
		prototype.setLocation(model.getLocation());

		prototype.setUser(model.getUser());
		
		model = prototype;
	}

}
