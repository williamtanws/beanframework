package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.populator.TemplateBasicPopulator;
import com.beanframework.core.converter.populator.TemplateFullPopulator;
import com.beanframework.core.data.TemplateDto;
import com.beanframework.template.domain.Template;
import com.beanframework.template.service.TemplateService;
import com.beanframework.template.specification.TemplateSpecification;

@Component
public class TemplateFacadeImpl implements TemplateFacade {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TemplateFacadeImpl.class);

	@Autowired
	private TemplateService templateService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private TemplateFullPopulator templateFullPopulator;

	@Autowired
	private TemplateBasicPopulator templateBasicPopulator;

	@Override
	public TemplateDto findOneByUuid(UUID uuid) throws Exception {
		Template entity = modelService.findOneByUuid(uuid, Template.class);

		return modelService.getDto(entity, TemplateDto.class, new DtoConverterContext(templateFullPopulator));
	}

	@Override
	public TemplateDto findOneProperties(Map<String, Object> properties) throws Exception {
		Template entity = modelService.findOneByProperties(properties, Template.class);

		return modelService.getDto(entity, TemplateDto.class, new DtoConverterContext(templateFullPopulator));
	}

	@Override
	public TemplateDto create(TemplateDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public TemplateDto update(TemplateDto model) throws BusinessException {
		return save(model);
	}

	public TemplateDto save(TemplateDto dto) throws BusinessException {
		try {
			Template entity = modelService.getEntity(dto, Template.class);
			entity = modelService.saveEntity(entity, Template.class);

			return modelService.getDto(entity, TemplateDto.class, new DtoConverterContext(templateFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Template.class);
	}

	@Override
	public Page<TemplateDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Template> page = modelService.findPage(TemplateSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Template.class);

		List<TemplateDto> dtos = modelService.getDto(page.getContent(), TemplateDto.class, new DtoConverterContext(templateBasicPopulator));
		return new PageImpl<TemplateDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Template.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = templateService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Template) {

				entityObject[0] = modelService.getDto(entityObject[0], TemplateDto.class, new DtoConverterContext(templateFullPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return templateService.findCountHistory(dataTableRequest);
	}

	@Override
	public TemplateDto createDto() throws Exception {
		Template template = modelService.create(Template.class);
		return modelService.getDto(template, TemplateDto.class, new DtoConverterContext(templateFullPopulator));
	}
}
