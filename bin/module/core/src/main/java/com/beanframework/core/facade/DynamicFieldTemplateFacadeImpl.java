package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.core.specification.DynamicFieldTemplateSpecification;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.dynamicfield.service.DynamicFieldTemplateService;

@Component
public class DynamicFieldTemplateFacadeImpl implements DynamicFieldTemplateFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private DynamicFieldTemplateService dynamicFieldTemplateService;

	@Override
	public DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws Exception {
		DynamicFieldTemplate entity = dynamicFieldTemplateService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, DynamicFieldTemplateDto.class);
	}

	@Override
	public DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties) throws Exception {
		DynamicFieldTemplate entity = dynamicFieldTemplateService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, DynamicFieldTemplateDto.class);
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
			entity = (DynamicFieldTemplate) dynamicFieldTemplateService.saveEntity(entity);

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, DynamicFieldTemplateDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		dynamicFieldTemplateService.deleteByUuid(uuid);
	}

	@Override
	public Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<DynamicFieldTemplate> page = dynamicFieldTemplateService.findEntityPage(dataTableRequest, DynamicFieldTemplateSpecification.getSpecification(dataTableRequest));
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<DynamicFieldTemplateDto> dtos = modelService.getDto(page.getContent(), context, DynamicFieldTemplateDto.class);
		return new PageImpl<DynamicFieldTemplateDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return dynamicFieldTemplateService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = dynamicFieldTemplateService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof DynamicFieldTemplate) {
				
				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(true);
				entityObject[0] = modelService.getDto(entityObject[0], context, DynamicFieldTemplateDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return dynamicFieldTemplateService.findCountHistory(dataTableRequest);
	}
}
