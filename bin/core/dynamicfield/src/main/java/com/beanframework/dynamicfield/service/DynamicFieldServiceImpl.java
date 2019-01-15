package com.beanframework.dynamicfield.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

@Service
public class DynamicFieldServiceImpl implements DynamicFieldService {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField create() throws Exception {
		return modelService.create(DynamicField.class);
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

	@Override
	public List<DynamicField> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findCachedEntityByPropertiesAndSorts(null, sorts, null, null, DynamicField.class);
	}
}
