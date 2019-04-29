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
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.service.DynamicFieldService;

@Component
public class DynamicFieldFacadeImpl implements DynamicFieldFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Override
	public DynamicFieldDto findOneByUuid(UUID uuid) throws Exception {
		DynamicField entity = dynamicFieldService.findOneEntityByUuid(uuid);
		DynamicFieldDto dto = modelService.getDto(entity, DynamicFieldDto.class, new DtoConverterContext(ConvertRelationType.ALL));

		return dto;
	}

	@Override
	public DynamicFieldDto findOneProperties(Map<String, Object> properties) throws Exception {
		DynamicField entity = dynamicFieldService.findOneEntityByProperties(properties);
		DynamicFieldDto dto = modelService.getDto(entity, DynamicFieldDto.class);

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
			entity = (DynamicField) dynamicFieldService.saveEntity(entity);

			return modelService.getDto(entity, DynamicFieldDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		dynamicFieldService.deleteByUuid(uuid);
	}

	@Override
	public Page<DynamicFieldDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<DynamicField> page = dynamicFieldService.findEntityPage(dataTableRequest);

		List<DynamicFieldDto> dtos = modelService.getDto(page.getContent(), DynamicFieldDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<DynamicFieldDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return dynamicFieldService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = dynamicFieldService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof DynamicField) {

				entityObject[0] = modelService.getDto(entityObject[0], DynamicFieldDto.class);
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

		return modelService.getDto(dynamicFieldService.create(), DynamicFieldDto.class);
	}

}
