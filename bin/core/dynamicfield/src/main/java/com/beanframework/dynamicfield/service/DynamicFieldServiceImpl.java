package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

@Service
public class DynamicFieldServiceImpl implements DynamicFieldService {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField create() throws Exception {
		return modelService.create(DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldOne", key = "#uuid")
	@Override
	public DynamicField findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldOneProperties", key = "#properties")
	@Override
	public DynamicField findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldsSorts", key = "#sorts")
	@Override
	public List<DynamicField> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldsPage", key = "{#query}")
	@Override
	public <T> Page<DynamicField> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, DynamicField.class);
	}

	@Cacheable(value = "DynamicFieldsHistory", key = "{#uuid, #firstResult, #maxResults}")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, DynamicField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldOneProperties", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "DynamicFieldOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsHistory", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsRelatedHistory", allEntries = true) }) //
	@Override
	public DynamicField saveEntity(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveEntity(model, DynamicField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "DynamicFieldOne", key = "#uuid"), //
			@CacheEvict(value = "DynamicFieldOneProperties", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsSorts", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsPage", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsHistory", allEntries = true), //
			@CacheEvict(value = "DynamicFieldsRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			DynamicField model = modelService.findOneEntityByUuid(uuid, DynamicField.class);
			modelService.deleteByEntity(model, DynamicField.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
