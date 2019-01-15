package com.beanframework.console.facade;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.console.data.ConfigurationDto;

@Component
public class ConfigurationFacadeImpl implements ConfigurationFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private ConfigurationService configurationService;

	@Override
	public Page<ConfigurationDto> findPage(Specification<ConfigurationDto> specification, PageRequest pageable) throws Exception {
		Page<Configuration> page = modelService.findEntityPage(specification, pageable,Configuration.class);
		List<ConfigurationDto> dtos = modelService.getDto(page.getContent(), ConfigurationDto.class);
		return new PageImpl<ConfigurationDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public ConfigurationDto findOneByUuid(UUID uuid) throws Exception {
		Configuration entity = modelService.findOneEntityByUuid(uuid, Configuration.class);
		return modelService.getDto(entity, ConfigurationDto.class);
	}

	@Override
	public ConfigurationDto findOneById(String id) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, id);
		Configuration entity = modelService.findOneEntityByProperties(properties, Configuration.class);
		return modelService.getDto(entity, ConfigurationDto.class);
	}

	@Override
	public ConfigurationDto createDto(ConfigurationDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public ConfigurationDto updateDto(ConfigurationDto model) throws BusinessException {
		return save(model);
	}
	
	public ConfigurationDto save(ConfigurationDto dto) throws BusinessException {
		try {
			Configuration entity = modelService.getEntity(dto, Configuration.class);
			entity = (Configuration) configurationService.saveEntity(entity);

			return modelService.getDto(entity, ConfigurationDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, Configuration.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Configuration.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], ConfigurationDto.class);
		}
		
		return revisions;
	}
}
