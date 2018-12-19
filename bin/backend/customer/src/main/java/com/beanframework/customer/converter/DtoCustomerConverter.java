package com.beanframework.customer.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;

public class DtoCustomerConverter implements DtoConverter<Customer, Customer> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DtoCustomerConverter.class);
	
	@Autowired
	private ModelService modelService;

	@Override
	public Customer convert(Customer source) throws ConverterException {
		modelService.detach(source);
		return convert(source, new Customer());
	}

	public List<Customer> convert(List<Customer> sources) throws ConverterException {
		List<Customer> convertedList = new ArrayList<Customer>();
		for (Customer source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Customer convert(Customer source, Customer prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		prototype.setAuthorities(source.getAuthorities());
		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		try {
			prototype.setUserGroups(modelService.getDto(source.getUserGroups()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
