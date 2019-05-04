package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.dynamicfield.service.DynamicFieldTemplateService;
import com.beanframework.dynamicfield.specification.DynamicFieldTemplateSpecification;

@Component
public class DynamicFieldTemplateFacadeImpl implements DynamicFieldTemplateFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private DynamicFieldTemplateService dynamicFieldTemplateService;

	@Override
	public DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws Exception {
		DynamicFieldTemplate entity = modelService.findByUuid(uuid, DynamicFieldTemplate.class);
		DynamicFieldTemplateDto dto = modelService.getDto(entity, DynamicFieldTemplateDto.class, new DtoConverterContext(ConvertRelationType.ALL));

		return dto;
	}

	@Override
	public DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties) throws Exception {
		DynamicFieldTemplate entity = modelService.findByProperties(properties, DynamicFieldTemplate.class);
		DynamicFieldTemplateDto dto = modelService.getDto(entity, DynamicFieldTemplateDto.class);

		return dto;
	}

	@Override
	public DynamicFieldTemplateDto create(DynamicFieldTemplateDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public DynamicFieldTemplateDto update(DynamicFieldTemplateDto model) throws BusinessException {
		return save(model);
	}

	public DynamicFieldTemplateDto save(DynamicFieldTemplateDto dto) throws BusinessException {
		try {
			DynamicFieldTemplate entity = modelService.getEntity(dto, DynamicFieldTemplate.class);
			entity = modelService.saveEntity(entity, DynamicFieldTemplate.class);

			return modelService.getDto(entity, DynamicFieldTemplateDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, DynamicFieldTemplate.class);
	}

	@Override
	public Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<DynamicFieldTemplate> page = modelService.findPage(DynamicFieldTemplateSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), DynamicFieldTemplate.class);

		List<DynamicFieldTemplateDto> dtos = modelService.getDto(page.getContent(), DynamicFieldTemplateDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<DynamicFieldTemplateDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(DynamicFieldTemplate.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = dynamicFieldTemplateService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof DynamicFieldTemplate) {

				entityObject[0] = modelService.getDto(entityObject[0], DynamicFieldTemplateDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return dynamicFieldTemplateService.findCountHistory(dataTableRequest);
	}

	@Override
	public DynamicFieldTemplateDto createDto() throws Exception {
		DynamicFieldTemplate dynamicFieldTemplate = modelService.create(DynamicFieldTemplate.class);
		return modelService.getDto(dynamicFieldTemplate, DynamicFieldTemplateDto.class);
	}
}
