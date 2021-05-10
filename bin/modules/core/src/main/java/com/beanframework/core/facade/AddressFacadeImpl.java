package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.specification.AddressSpecification;
import com.beanframework.user.domain.Address;

@Component
public class AddressFacadeImpl extends AbstractFacade<Address, AddressDto> implements AddressFacade {

	private static final Class<Address> entityClass = Address.class;
	private static final Class<AddressDto> dtoClass = AddressDto.class;

	@Override
	public AddressDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public AddressDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public AddressDto create(AddressDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public AddressDto update(AddressDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<AddressDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, AddressSpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
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
	public AddressDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

}
