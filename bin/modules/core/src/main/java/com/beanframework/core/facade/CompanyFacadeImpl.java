package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.specification.CompanySpecification;
import com.beanframework.user.domain.Company;

@Component
public class CompanyFacadeImpl extends AbstractFacade<Company, CompanyDto> implements CompanyFacade {
	
	private static final Class<Company> entityClass = Company.class;
	private static final Class<CompanyDto> dtoClass = CompanyDto.class;

	@Override
	public CompanyDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public CompanyDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public CompanyDto create(CompanyDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public CompanyDto update(CompanyDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<CompanyDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, CompanySpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
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
	public CompanyDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

}
