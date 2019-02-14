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
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.specification.DynamicFieldSlotSpecification;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.service.DynamicFieldSlotService;

@Component
public class DynamicFieldSlotFacadeImpl implements DynamicFieldSlotFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private DynamicFieldSlotService dynamicFieldService;

	@Override
	public DynamicFieldSlotDto findOneByUuid(UUID uuid) throws Exception {
		DynamicFieldSlot entity = dynamicFieldService.findOneEntityByUuid(uuid);

		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, DynamicFieldSlotDto.class);
	}

	@Override
	public DynamicFieldSlotDto findOneProperties(Map<String, Object> properties) throws Exception {
		DynamicFieldSlot entity = dynamicFieldService.findOneEntityByProperties(properties);

		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, DynamicFieldSlotDto.class);
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
			entity = (DynamicFieldSlot) dynamicFieldService.saveEntity(entity);

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, DynamicFieldSlotDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		dynamicFieldService.deleteByUuid(uuid);
	}

	@Override
	public Page<DynamicFieldSlotDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<DynamicFieldSlot> page = dynamicFieldService.findEntityPage(dataTableRequest, DynamicFieldSlotSpecification.getSpecification(dataTableRequest));

		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<DynamicFieldSlotDto> dtos = modelService.getDto(page.getContent(), context, DynamicFieldSlotDto.class);
		return new PageImpl<DynamicFieldSlotDto>(dtos, page.getPageable(), page.getTotalElements());
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
			if (entityObject[0] instanceof DynamicFieldSlot) {

				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(true);
				entityObject[0] = modelService.getDto(entityObject[0], context, DynamicFieldSlotDto.class);
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
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(dynamicFieldService.create(), context, DynamicFieldSlotDto.class);
	}

}
