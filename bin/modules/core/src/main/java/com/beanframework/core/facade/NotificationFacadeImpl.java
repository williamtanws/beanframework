package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.core.specification.NotificationSpecification;
import com.beanframework.notification.domain.Notification;

@Component
public class NotificationFacadeImpl extends AbstractFacade<Notification, NotificationDto> implements NotificationFacade {

	private static final Class<Notification> entityClass = Notification.class;
	private static final Class<NotificationDto> dtoClass = NotificationDto.class;

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
	public List<NotificationDto> findList(Map<String, Object> properties, Map<String, Sort.Direction> sorts, Integer firstResult, Integer maxResult) throws Exception {
		List<Notification> list = modelService.findByPropertiesBySortByResult(properties, sorts, firstResult, maxResult, entityClass);
		return modelService.getDtoList(list, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public NotificationDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

}
