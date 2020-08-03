package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.entity.EntityCustomerProfileConverter;
import com.beanframework.core.converter.populator.CustomerBasicPopulator;
import com.beanframework.core.converter.populator.CustomerFullPopulator;
import com.beanframework.core.converter.populator.history.CustomerHistoryPopulator;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;
import com.beanframework.customer.specification.CustomerSpecification;
import com.beanframework.user.service.UserService;

@Component
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerFullPopulator customerFullPopulator;

	@Autowired
	private CustomerBasicPopulator customerBasicPopulator;

	@Autowired
	private CustomerHistoryPopulator customerHistoryPopulator;

	@Autowired
	private EntityCustomerProfileConverter entityCustomerProfileConverter;

	@Override
	public CustomerDto findOneByUuid(UUID uuid) throws Exception {
		Customer entity = modelService.findOneByUuid(uuid, Customer.class);
		CustomerDto dto = modelService.getDto(entity, CustomerDto.class, new DtoConverterContext(customerFullPopulator));

		return dto;
	}

	@Override
	public CustomerDto findOneProperties(Map<String, Object> properties) throws Exception {
		Customer entity = modelService.findOneByProperties(properties, Customer.class);
		CustomerDto dto = modelService.getDto(entity, CustomerDto.class, new DtoConverterContext(customerFullPopulator));

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
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == Boolean.FALSE) {
					throw new Exception("Wrong picture format");
				}
			}

			Customer entity = modelService.getEntity(dto, Customer.class);
			entity = modelService.saveEntity(entity, Customer.class);

			userService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, CustomerDto.class, new DtoConverterContext(customerFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Customer.class);
	}

	@Override
	public Page<CustomerDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Customer> page = modelService.findPage(CustomerSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Customer.class);

		List<CustomerDto> dtos = modelService.getDto(page.getContent(), CustomerDto.class, new DtoConverterContext(customerBasicPopulator));
		return new PageImpl<CustomerDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Customer.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = customerService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Customer) {

				entityObject[0] = modelService.getDto(entityObject[0], CustomerDto.class, new DtoConverterContext(customerHistoryPopulator));
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
		Customer customer = modelService.create(Customer.class);
		return modelService.getDto(customer, CustomerDto.class, new DtoConverterContext(customerFullPopulator));
	}

	@Override
	public CustomerDto saveProfile(CustomerDto dto) throws BusinessException {

		try {
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == Boolean.FALSE) {
					throw new Exception("Wrong picture format");
				}
			}
			Customer entity = entityCustomerProfileConverter.convert(dto);

			entity = modelService.saveEntity(entity, Customer.class);
			customerService.updatePrincipal(entity);
			userService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, CustomerDto.class, new DtoConverterContext(customerFullPopulator));

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public CustomerDto getCurrentUser() throws Exception {
		Customer entity = customerService.getCurrentUser();

		return modelService.getDto(entity, CustomerDto.class, new DtoConverterContext(customerFullPopulator));
	}

}
