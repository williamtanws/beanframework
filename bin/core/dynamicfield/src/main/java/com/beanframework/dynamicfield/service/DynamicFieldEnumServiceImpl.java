package com.beanframework.dynamicfield.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

@Service
public class DynamicFieldEnumServiceImpl implements DynamicFieldEnumService {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldEnum create() throws Exception {
		return modelService.create(DynamicFieldEnum.class);
	}

	@Cacheable(value = "DynamicFieldEnumOne", key = "#uuid")
	@Override
	public DynamicFieldEnum findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, DynamicFieldEnum.class);
	}

	@Cacheable(value = "DynamicFieldEnumOneProperties", key = "#properties")
	@Override
	public DynamicFieldEnum findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, DynamicFieldEnum.class);
	}

	@Cacheable(value = "DynamicFieldEnumsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<DynamicFieldEnum> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, DynamicFieldEnum.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldEnumOneProperties", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "DynamicFieldEnumOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldEnumsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldEnumsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldEnumsHistory", allEntries = true) }) //
	@Override
	public DynamicFieldEnum saveEntity(DynamicFieldEnum model) throws BusinessException {
		return (DynamicFieldEnum) modelService.saveEntity(model, DynamicFieldEnum.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldEnumOne", key = "#uuid"), //
			@CacheEvict(value = "DynamicFieldEnumOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldEnumsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldEnumsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldEnumsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicFieldEnum model = modelService.findOneEntityByUuid(uuid, true, DynamicFieldEnum.class);
			modelService.deleteByEntity(model, DynamicFieldEnum.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "DynamicFieldEnumsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<DynamicFieldEnum> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception {
		return modelService.findEntityPage(dataTableRequest.getSpecification(), dataTableRequest.getPageable(), false, DynamicFieldEnum.class);
	}

	@Cacheable(value = "DynamicFieldEnumsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(DynamicFieldEnum.class);
	}

	@Cacheable(value = "DynamicFieldEnumsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldEnum.class);

	}

	@Cacheable(value = "DynamicFieldEnumsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), DynamicFieldEnum.class);
	}
}
