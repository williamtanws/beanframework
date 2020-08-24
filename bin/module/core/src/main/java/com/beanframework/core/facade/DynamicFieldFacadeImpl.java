package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.populator.DynamicFieldBasicPopulator;
import com.beanframework.core.converter.populator.DynamicFieldFullPopulator;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.service.DynamicFieldService;
import com.beanframework.dynamicfield.specification.DynamicFieldSpecification;

@Component
public class DynamicFieldFacadeImpl implements DynamicFieldFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Autowired
	private DynamicFieldFullPopulator dynamicFieldFullPopulator;

	@Autowired
	private DynamicFieldBasicPopulator dynamicFieldBasicPopulator;
	
	@Override
	public DynamicFieldDto findOneByUuid(UUID uuid) throws Exception {
		DynamicField entity = modelService.findOneByUuid(uuid, DynamicField.class);
		DynamicFieldDto dto = modelService.getDto(entity, DynamicFieldDto.class, new DtoConverterContext(dynamicFieldFullPopulator));

		return dto;
	}

	@Override
	public DynamicFieldDto findOneProperties(Map<String, Object> properties) throws Exception {
		DynamicField entity = modelService.findOneByProperties(properties, DynamicField.class);
		DynamicFieldDto dto = modelService.getDto(entity, DynamicFieldDto.class, new DtoConverterContext(dynamicFieldFullPopulator));

		return dto;
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
			entity = modelService.saveEntity(entity, DynamicField.class);

			return modelService.getDto(entity, DynamicFieldDto.class, new DtoConverterContext(dynamicFieldFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, DynamicField.class);
	}

	@Override
	public Page<DynamicFieldDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<DynamicField> page = modelService.findPage(DynamicFieldSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), DynamicField.class);

		List<DynamicFieldDto> dtos = modelService.getDto(page.getContent(), DynamicFieldDto.class, new DtoConverterContext(dynamicFieldBasicPopulator));
		return new PageImpl<DynamicFieldDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(DynamicField.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = dynamicFieldService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof DynamicField) {

				entityObject[0] = modelService.getDto(entityObject[0], DynamicFieldDto.class, new DtoConverterContext(dynamicFieldFullPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return dynamicFieldService.findCountHistory(dataTableRequest);
	}

	@Override
	public DynamicFieldDto createDto() throws Exception {
		DynamicField dynamicField = modelService.create(DynamicField.class);
		return modelService.getDto(dynamicField, DynamicFieldDto.class, new DtoConverterContext(dynamicFieldFullPopulator));
	}

}
