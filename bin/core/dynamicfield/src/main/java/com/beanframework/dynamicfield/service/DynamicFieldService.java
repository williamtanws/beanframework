package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicField;

public interface DynamicFieldService {

	DynamicField create() throws Exception;

	DynamicField saveEntity(DynamicField model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

	List<DynamicField> findEntityBySorts(Map<String, Direction> sorts) throws Exception;
}
