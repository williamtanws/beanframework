package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.EntityCustomerProfileConverter;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;

@Component
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EntityCustomerProfileConverter entityCustomerProfileConverter;

	@Override
	public CustomerDto findOneByUuid(UUID uuid) throws Exception {
		Customer entity = customerService.findOneEntityByUuid(uuid);
		CustomerDto dto = modelService.getDto(entity, CustomerDto.class);

		return dto;
	}

	@Override
	public CustomerDto findOneProperties(Map<String, Object> properties) throws Exception {
		Customer entity = customerService.findOneEntityByProperties(properties);
		CustomerDto dto = modelService.getDto(entity, CustomerDto.class);

		return dto;
	}

	@Override
	public CustomerDto create(CustomerDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public CustomerDto update(CustomerDto model) throws BusinessException {
		return save(model);
	}

	public CustomerDto save(CustomerDto dto) throws BusinessException {
		try {
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == false) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == false) {
					throw new Exception("Wrong picture format");
				}
			}

			Customer entity = modelService.getEntity(dto, Customer.class);
			entity = (Customer) customerService.saveEntity(entity);

			customerService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, CustomerDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		customerService.deleteByUuid(uuid);
	}

	@Override
	public Page<CustomerDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Customer> page = customerService.findEntityPage(dataTableRequest);

		List<CustomerDto> dtos = modelService.getDto(page.getContent(), CustomerDto.class);
		return new PageImpl<CustomerDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return customerService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = customerService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Customer) {

				entityObject[0] = modelService.getDto(entityObject[0], CustomerDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return customerService.findCountHistory(dataTableRequest);
	}

	@Override
	public CustomerDto createDto() throws Exception {

		return modelService.getDto(customerService.create(), CustomerDto.class);
	}

	@Override
	public CustomerDto saveProfile(CustomerDto dto) throws BusinessException {

		try {
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == false) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == false) {
					throw new Exception("Wrong picture format");
				}
			}
			Customer entity = entityCustomerProfileConverter.convert(dto);

			entity = (Customer) customerService.saveEntity(entity);
			customerService.updatePrincipal(entity);
			customerService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, CustomerDto.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public CustomerDto getCurrentUser() throws Exception {
		Customer entity = customerService.getCurrentUser();

		return modelService.getDto(entity, CustomerDto.class);
	}

}
