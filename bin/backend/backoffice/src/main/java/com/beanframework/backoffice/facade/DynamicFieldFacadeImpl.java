package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.DynamicFieldDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.service.DynamicFieldService;

@Component
public class DynamicFieldFacadeImpl implements DynamicFieldFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Override
	public Page<DynamicFieldDto> findPage(Specification<DynamicFieldDto> specification, PageRequest pageable) throws Exception {
		Page<DynamicField> page = modelService.findEntityPage(specification, pageable, DynamicField.class);
		List<DynamicFieldDto> dtos = modelService.getDto(page.getContent(), DynamicFieldDto.class);
		return new PageImpl<DynamicFieldDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public DynamicFieldDto findOneByUuid(UUID uuid) throws Exception {
		DynamicField entity = modelService.findOneEntityByUuid(uuid, DynamicField.class);
		return modelService.getDto(entity, DynamicFieldDto.class);
	}

	@Override
	public DynamicFieldDto create(DynamicFieldDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public DynamicFieldDto update(DynamicFieldDto model) throws BusinessException {
		return save(model);
	}

	public DynamicFieldDto save(DynamicFieldDto dto) throws BusinessException {
		try {
			DynamicField entity = modelService.getEntity(dto, DynamicField.class);
			entity = (DynamicField) dynamicFieldService.saveEntity(entity);

			return modelService.getDto(entity, DynamicFieldDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, DynamicField.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public DynamicFieldDto findOneByProperties(Map<String, Object> properties) throws Exception {
		DynamicField entity = modelService.findCachedEntityByPropertiesAndSorts(properties, null, null, null, DynamicField.class);
		return modelService.getDto(entity, DynamicFieldDto.class);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, DynamicField.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], DynamicFieldDto.class);
		}

		return revisions;
	}

	@Override
	public List<DynamicFieldDto> findAllDtoDynamicFields() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(DynamicField.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(dynamicFieldService.findEntityBySorts(sorts), DynamicFieldDto.class);
	}

}
