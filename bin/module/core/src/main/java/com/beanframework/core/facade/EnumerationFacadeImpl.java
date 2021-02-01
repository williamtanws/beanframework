package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.enumuration.specification.EnumerationSpecification;

@Component
public class EnumerationFacadeImpl extends AbstractFacade<Enumeration, EnumerationDto> implements EnumerationFacade {
	
	private static final Class<Enumeration> entityClass = Enumeration.class;
	private static final Class<EnumerationDto> dtoClass = EnumerationDto.class;

	@Override
	public EnumerationDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public EnumerationDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public EnumerationDto create(EnumerationDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public EnumerationDto update(EnumerationDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, EnumerationSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public EnumerationDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

}
