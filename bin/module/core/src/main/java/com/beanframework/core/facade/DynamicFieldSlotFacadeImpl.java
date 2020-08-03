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
import com.beanframework.core.converter.populator.DynamicFieldSlotBasicPopulator;
import com.beanframework.core.converter.populator.DynamicFieldSlotFullPopulator;
import com.beanframework.core.converter.populator.history.DynamicFieldSlotHistoryPopulator;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.service.DynamicFieldSlotService;
import com.beanframework.dynamicfield.specification.DynamicFieldSlotSpecification;

@Component
public class DynamicFieldSlotFacadeImpl implements DynamicFieldSlotFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private DynamicFieldSlotService dynamicFieldService;

	@Autowired
	private DynamicFieldSlotFullPopulator dynamicFieldSlotFullPopulator;

	@Autowired
	private DynamicFieldSlotBasicPopulator dynamicFieldSlotBasicPopulator;

	@Autowired
	private DynamicFieldSlotHistoryPopulator dynamicFieldSlotHistoryPopulator;

	@Override
	public DynamicFieldSlotDto findOneByUuid(UUID uuid) throws Exception {
		DynamicFieldSlot entity = modelService.findOneByUuid(uuid, DynamicFieldSlot.class);
		DynamicFieldSlotDto dto = modelService.getDto(entity, DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotFullPopulator));

		return dto;
	}

	@Override
	public DynamicFieldSlotDto findOneProperties(Map<String, Object> properties) throws Exception {
		DynamicFieldSlot entity = modelService.findOneByProperties(properties, DynamicFieldSlot.class);
		DynamicFieldSlotDto dto = modelService.getDto(entity, DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotFullPopulator));

		return dto;
	}

	@Override
	public DynamicFieldSlotDto create(DynamicFieldSlotDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public DynamicFieldSlotDto update(DynamicFieldSlotDto model) throws BusinessException {
		return save(model);
	}

	public DynamicFieldSlotDto save(DynamicFieldSlotDto dto) throws BusinessException {
		try {
			DynamicFieldSlot entity = modelService.getEntity(dto, DynamicFieldSlot.class);
			entity = modelService.saveEntity(entity, DynamicFieldSlot.class);

			return modelService.getDto(entity, DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, DynamicFieldSlot.class);
	}

	@Override
	public Page<DynamicFieldSlotDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<DynamicFieldSlot> page = modelService.findPage(DynamicFieldSlotSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), DynamicFieldSlot.class);

		List<DynamicFieldSlotDto> dtos = modelService.getDto(page.getContent(), DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotBasicPopulator));
		return new PageImpl<DynamicFieldSlotDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(DynamicFieldSlot.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = dynamicFieldService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof DynamicFieldSlot) {

				entityObject[0] = modelService.getDto(entityObject[0], DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotHistoryPopulator));
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
	public DynamicFieldSlotDto createDto() throws Exception {
		DynamicFieldSlot dynamicFieldSlot= modelService.create(DynamicFieldSlot.class);
		return modelService.getDto(dynamicFieldSlot, DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotFullPopulator));
	}

}
