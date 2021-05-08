package com.beanframework.core.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.BeforeRemoveEvent;
import com.beanframework.common.registry.BeforeRemoveListener;
import com.beanframework.common.service.ModelService;
import com.beanframework.imex.domain.Imex;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Component
public class CoreBeforeRemoveListener implements BeforeRemoveListener {

	@Autowired
	private ModelService modelService;

	@Autowired
	private UserService userService;

	@Autowired
	private MediaService mediaService;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Override
	public void beforeRemove(final Object model, final BeforeRemoveEvent event) throws ListenerException {

		try {
			if (model instanceof User) {
				User user = (User) model;
				userService.deleteProfilePictureFileByUuid(user.getUuid());

			} else if (model instanceof Media) {
				Media media = (Media) model;
				mediaService.removeFile(media);

			} else if (model instanceof Imex) {
				Imex imex = modelService.findOneByUuid(((Imex) model).getUuid(), Imex.class);
				for (UUID media : imex.getMedias()) {
					mediaService.removeMedia(media);
				}

			}

		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}
}
