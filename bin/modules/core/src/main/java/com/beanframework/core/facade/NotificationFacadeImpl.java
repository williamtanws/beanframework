package com.beanframework.core.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.core.specification.NotificationSpecification;
import com.beanframework.notification.NotificationConstants;
import com.beanframework.notification.domain.Notification;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Component
public class NotificationFacadeImpl extends AbstractFacade<Notification, NotificationDto> implements NotificationFacade {

	private static final Class<Notification> entityClass = Notification.class;
	private static final Class<NotificationDto> dtoClass = NotificationDto.class;

	@Autowired
	private UserService userService;

	@Override
	public NotificationDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public NotificationDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public NotificationDto create(NotificationDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<NotificationDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, NotificationSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public NotificationDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

	@Override
	public List<NotificationDto> findAllNewNotificationByUser(UUID uuid) throws Exception {
		User user = modelService.findOneByUuid(uuid, User.class);
		List<Notification> notifications = null;

		if (user.getParameters().get(NotificationConstants.USER_NOTIFICATION) != null) {
			Date userNotificationDate = NotificationConstants.USER_NOTIFICATION_DATEFORMAT.parse(user.getParameters().get(NotificationConstants.USER_NOTIFICATION));

			notifications = modelService.findBySpecification(NotificationSpecification.getNewNotificationByFromDate(userNotificationDate), entityClass);
		} else {
			notifications = modelService.findBySpecification(NotificationSpecification.getNewNotificationByFromDate(null), entityClass);
		}

		return modelService.getDtoList(notifications, dtoClass);
	}

	@Override
	public void refreshAllNewNotificationByUser(UUID uuid) throws Exception {
		// Do nothing, just evict cache from interface
	}

	@Override
	public void checkedNotification(UUID uuid) throws Exception {
		User user = modelService.findOneByUuid(uuid, User.class);
		user.getParameters().put(NotificationConstants.USER_NOTIFICATION, NotificationConstants.USER_NOTIFICATION_DATEFORMAT.format(new Date()));
		modelService.saveEntity(user);
		userService.updatePrincipal(user);
	}

	@Override
	public int removeAllNotification() throws BusinessException {
		int count = 0;
		
		List<Notification> oldNotification = modelService.findAll(entityClass);
		count = oldNotification.size();

		for (Notification notification : oldNotification) {
			modelService.deleteEntity(notification, entityClass);
		}

		return count;
	}

	@Override
	public int removeOldNotificationByToDate(Date date) throws Exception {
		int count = 0;
		
		List<Notification> oldNotification = modelService.findBySpecification(NotificationSpecification.getOldNotificationByToDate(date), entityClass);
		count = oldNotification.size();

		for (Notification notification : oldNotification) {
			modelService.deleteEntity(notification, entityClass);
		}
		return count;
	}

}
