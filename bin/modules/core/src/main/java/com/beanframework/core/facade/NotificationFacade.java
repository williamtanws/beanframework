package com.beanframework.core.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.notification.NotificationConstants;

public interface NotificationFacade {
	NotificationDto findOneByUuid(UUID uuid) throws Exception;

	NotificationDto findOneProperties(Map<String, Object> properties) throws Exception;

	@CacheEvict(value = NotificationConstants.CACHE_NOTIFICATIONS, allEntries = true)
	NotificationDto create(NotificationDto model) throws BusinessException;

	@CacheEvict(value = NotificationConstants.CACHE_NOTIFICATIONS, allEntries = true)
	void delete(UUID uuid) throws BusinessException;

	Page<NotificationDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	NotificationDto createDto() throws Exception;

	@Cacheable(value = NotificationConstants.CACHE_NOTIFICATIONS, key = "#uuid")
	List<NotificationDto> findAllNewNotificationByUser(UUID uuid) throws Exception;

	@CacheEvict(value = NotificationConstants.CACHE_NOTIFICATIONS, key = "#uuid")
	void refreshAllNewNotificationByUser(UUID uuid) throws Exception;

	@CacheEvict(value = NotificationConstants.CACHE_NOTIFICATIONS, key = "#uuid")
	void checkedNotification(UUID uuid) throws Exception;

	int removeAllNotification() throws BusinessException;

	int removeOldNotificationByToDate(Date date) throws Exception;
}
