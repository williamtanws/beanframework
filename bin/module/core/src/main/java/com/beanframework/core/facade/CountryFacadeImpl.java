package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.specification.CountrySpecification;
import com.beanframework.internationalization.domain.Country;

@Component
public class CountryFacadeImpl extends AbstractFacade<Country, CountryDto> implements CountryFacade {
	
	private static final Class<Country> entityClass = Country.class;
	private static final Class<CountryDto> dtoClass = CountryDto.class;

	@Override
	public CountryDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public CountryDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public CountryDto create(CountryDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public CountryDto update(CountryDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<CountryDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, CountrySpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
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
	public CountryDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

}
