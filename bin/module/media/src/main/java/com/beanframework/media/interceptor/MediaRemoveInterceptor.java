package com.beanframework.media.interceptor;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;

public class MediaRemoveInterceptor extends AbstractRemoveInterceptor<Media> {

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Override
	public void onRemove(Media model, InterceptorContext context) throws InterceptorException {
		File mediaFolder = new File(MEDIA_LOCATION + File.separator + model.getFolder() + File.separator + model.getUuid().toString());
		FileUtils.deleteQuietly(mediaFolder);
	}

}
