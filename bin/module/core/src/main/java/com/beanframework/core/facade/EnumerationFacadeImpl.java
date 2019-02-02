package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.specification.EnumerationSpecification;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.enumuration.service.EnumerationService;

@Component
public class EnumerationFacadeImpl implements EnumerationFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EnumerationService enumerationService;

	@Override
	public EnumerationDto findOneByUuid(UUID uuid) throws Exception {
		Enumeration entity = enumerationService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, EnumerationDto.class);
	}

	@Override
	public EnumerationDto findOneProperties(Map<String, Object> properties) throws Exception {
		Enumeration entity = enumerationService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, EnumerationDto.class);
	}

	@Override
	public EnumerationDto create(EnumerationDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public EnumerationDto update(EnumerationDto model) throws BusinessException {
		return save(model);
	}

	public EnumerationDto save(EnumerationDto dto) throws BusinessException {
		try {
			Enumeration entity = modelService.getEntity(dto, Enumeration.class);
			entity = (Enumeration) enumerationService.saveEntity(entity);

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, EnumerationDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		enumerationService.deleteByUuid(uuid);
	}

	@Override
	public Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Enumeration> page = enumerationService.findEntityPage(dataTableRequest, EnumerationSpecification.getSpecification(dataTableRequest));
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<EnumerationDto> dtos = modelService.getDto(page.getContent(), context, EnumerationDto.class);
		return new PageImpl<EnumerationDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return enumerationService.count();
	}
	
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = enumerationService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Enumeration) {
				
				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(true);
				entityObject[0] = modelService.getDto(entityObject[0], context, EnumerationDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}
	
	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return enumerationService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<EnumerationDto> findAllDtoEnums() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Enumeration.CREATED_DATE, Sort.Direction.DESC);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		return modelService.getDto(enumerationService.findEntityBySorts(sorts, false), context, EnumerationDto.class);
	}

}
