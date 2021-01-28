package com.beanframework.core.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;

public class AbstractFacade<ENTITY extends GenericEntity, DTO extends GenericDto> {

	@Autowired
	protected ModelService modelService;

	public DTO findOneByUuid(UUID uuid, Class<ENTITY> entityClass, Class<DTO> dtoClass) throws Exception {
		return modelService.getDto(modelService.findOneByUuid(uuid, entityClass), dtoClass);
	}

	public DTO findOneProperties(Map<String, Object> properties, Class<ENTITY> entityClass, Class<DTO> dtoClass) throws Exception {
		return modelService.getDto(modelService.findOneByProperties(properties, entityClass), dtoClass);
	}

	public Page<DTO> findPage(DataTableRequest dataTableRequest, Specification<ENTITY> specification, Class<ENTITY> entityClass, Class<DTO> dtoClass) throws Exception {
		Page<ENTITY> page = modelService.findPage(specification, dataTableRequest.getPageable(), entityClass);

		List<DTO> dtos = modelService.getDtoList(page.getContent(), dtoClass);
		return new PageImpl<DTO>(dtos, page.getPageable(), page.getTotalElements());
	}

	public int count(Class<ENTITY> entityClass) throws Exception {
		return modelService.countAll(entityClass);
	}

	public List<Object[]> findHistory(DataTableRequest dataTableRequest, Class<ENTITY> entityClass) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), entityClass);
	}

	public int findCountHistory(DataTableRequest dataTableRequest, Class<ENTITY> entityClass) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.countHistory(false, auditCriterions, auditOrders, entityClass);
	}

	public DTO save(DTO dto, Class<ENTITY> entityClass, Class<DTO> dtoClass) throws BusinessException {
		try {
			ENTITY entity = modelService.getEntity(dto, entityClass);
			entity = modelService.saveEntity(entity);

			return modelService.getDto(entity, dtoClass);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public void delete(UUID uuid, Class<ENTITY> entityClass) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, entityClass);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public DTO createDto(Class<ENTITY> entityClass, Class<DTO> dtoClass) throws Exception {
		ENTITY entity = modelService.create(entityClass);
		return modelService.getDto(entity, dtoClass);
	}
}
