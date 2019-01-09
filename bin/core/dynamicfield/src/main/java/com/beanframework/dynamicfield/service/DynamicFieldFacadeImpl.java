package com.beanframework.dynamicfield.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class DynamicFieldFacadeImpl implements DynamicFieldFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public Page<DynamicField> findDtoPage(Specification<DynamicField> specification, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(specification, pageable, DynamicField.class);
	}

	@Override
	public DynamicField create() throws Exception {
		return modelService.create(DynamicField.class);
	}

	@Override
	public DynamicField findOneDtoByUuid(UUID uuid) throws Exception {
		return modelService.findOneDtoByUuid(uuid, DynamicField.class);
	}

	@Override
	public DynamicField findOneDtoById(String id) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicField.ID, id);

		return modelService.findOneDtoByProperties(properties, DynamicField.class);
	}

	@Override
	public DynamicField createDto(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveDto(model, DynamicField.class);
	}

	@Override
	public DynamicField updateDto(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveDto(model, DynamicField.class);
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
	public DynamicField saveEntity(DynamicField model) throws BusinessException {
		return (DynamicField) modelService.saveEntity(model, DynamicField.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicField.ID, id);
			DynamicField model = modelService.findOneEntityByProperties(properties, DynamicField.class);
			modelService.deleteByEntity(model, DynamicField.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
