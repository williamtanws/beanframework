package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.customer.domain.Customer;

public class DtoCustomerConverter implements DtoConverter<Customer, CustomerDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCustomerConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public CustomerDto convert(Customer source, DtoConverterContext context) throws ConverterException {
		return convert(source, new CustomerDto(), context);
	}

	public List<CustomerDto> convert(List<Customer> sources, DtoConverterContext context) throws ConverterException {
		List<CustomerDto> convertedList = new ArrayList<CustomerDto>();
		for (Customer source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private CustomerDto convert(Customer source, CustomerDto prototype, DtoConverterContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());

		try {
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));

			prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
