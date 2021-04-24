package com.beanframework.core.listener;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.AfterSaveEvent;
import com.beanframework.common.registry.AfterSaveListener;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.imex.domain.Imex;
import com.beanframework.imex.service.ImexService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.service.UserService;

public class CoreAfterSaveListener implements AfterSaveListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(CoreAfterSaveListener.class);

	@Autowired
	private AuditorService auditorService;

	@Autowired
	private UserService userService;

	@Autowired
	private ImexService imexService;

	@Override
	public void afterSave(final Object model, final AfterSaveEvent event) throws ListenerException {

		try {
			if (model instanceof User) {
				User user = (User) model;

				auditorService.saveEntityByUser(user);

				if (StringUtils.isNotBlank(user.getProfilePicture())) {
					ClassPathResource resource = new ClassPathResource(user.getProfilePicture());
					try {
						userService.saveProfilePicture(user, resource.getInputStream());
					} catch (IOException e) {
						e.printStackTrace();
						LOGGER.error(e.getMessage(), e);
					}
				}

			} else if (model instanceof Imex) {
				Imex imex = (Imex) model;
				imexService.importExportMedia(imex);

			} else if (model instanceof Menu) {
			} else if (model instanceof Configuration) {
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ListenerException(e.getMessage(), e);
		}

	}
}
