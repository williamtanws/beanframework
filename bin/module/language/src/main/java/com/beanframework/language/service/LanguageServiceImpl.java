package com.beanframework.language.service;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private ModelService modelService;

	@Override
	public Language create() throws Exception {
		return modelService.create(Language.class);
	}

	@Cacheable(value = "LanguageOne", key = "#uuid")
	@Override
	public Language findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Language.class);
	}

	@Cacheable(value = "LanguageOneProperties", key = "#properties")
	@Override
	public Language findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Language.class);
	}

	@Cacheable(value = "LanguagesSorts", key = "'sorts:'+#sorts")
	@Override
	public List<Language> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Language.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "LanguageOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "LanguageOneProperties", allEntries = true), //
			@CacheEvict(value = "LanguagesSorts", allEntries = true), //
			@CacheEvict(value = "LanguagesPage", allEntries = true), //
			@CacheEvict(value = "LanguagesHistory", allEntries = true) }) //
	@Override
	public Language saveEntity(Language model) throws BusinessException {
		return (Language) modelService.saveEntity(model, Language.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "LanguageOne", key = "#uuid"), //
			@CacheEvict(value = "LanguageOneProperties", allEntries = true), //
			@CacheEvict(value = "LanguagesSorts", allEntries = true), //
			@CacheEvict(value = "LanguagesPage", allEntries = true), //
			@CacheEvict(value = "LanguagesHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Language model = modelService.findOneEntityByUuid(uuid, Language.class);
			modelService.deleteByEntity(model, Language.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "LanguagesPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Language> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), Language.class);
	}

	@Cacheable(value = "LanguagesPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Language.class);
	}

	@Cacheable(value = "LanguagesHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Language.class);
		return histories;
	}

	@Cacheable(value = "LanguagesHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Language.class);
	}
}
