package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ImexDto;
import com.beanframework.imex.domain.Imex;
import com.beanframework.imex.specification.ImexSpecification;

@Component
public class ImexFacadeImpl extends AbstractFacade<Imex, ImexDto> implements ImexFacade {
	
	private static final Class<Imex> entityClass = Imex.class;
	private static final Class<ImexDto> dtoClass = ImexDto.class;

	@Override
	public ImexDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public ImexDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public ImexDto create(ImexDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public ImexDto update(ImexDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<ImexDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, ImexSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
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
	public ImexDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
