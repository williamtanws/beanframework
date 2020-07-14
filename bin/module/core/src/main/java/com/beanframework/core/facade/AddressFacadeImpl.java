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

import com.beanframework.address.domain.Address;
import com.beanframework.address.service.AddressService;
import com.beanframework.address.specification.AddressSpecification;
import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AddressDto;

@Component
public class AddressFacadeImpl implements AddressFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AddressService addressService;

	@Override
	public AddressDto findOneByUuid(UUID uuid) throws Exception {
		Address entity = modelService.findOneByUuid(uuid, Address.class);

		return modelService.getDto(entity, AddressDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public AddressDto findOneProperties(Map<String, Object> properties) throws Exception {
		Address entity = modelService.findOneByProperties(properties, Address.class);

		return modelService.getDto(entity, AddressDto.class);
	}

	@Override
	public AddressDto create(AddressDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public AddressDto update(AddressDto model) throws BusinessException {
		return save(model);
	}

	public AddressDto save(AddressDto dto) throws BusinessException {
		try {
			Address entity = modelService.getEntity(dto, Address.class);
			entity = modelService.saveEntity(entity, Address.class);

			return modelService.getDto(entity, AddressDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Address.class);
	}

	@Override
	public Page<AddressDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Address> page = modelService.findPage(AddressSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Address.class);

		List<AddressDto> dtos = modelService.getDto(page.getContent(), AddressDto.class, new DtoConverterContext(ConvertRelationType.BASIC));
		return new PageImpl<AddressDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Address.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = addressService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Address) {

				entityObject[0] = modelService.getDto(entityObject[0], AddressDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return addressService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<AddressDto> findAllDtoAddresss() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Address.CREATED_DATE, Sort.Direction.DESC);

		List<Address> addresss = modelService.findByPropertiesBySortByResult(null, sorts, null, null, Address.class);
		return modelService.getDto(addresss, AddressDto.class);
	}

	@Override
	public AddressDto createDto() throws Exception {
		Address address = modelService.create(Address.class);
		return modelService.getDto(address, AddressDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

}
