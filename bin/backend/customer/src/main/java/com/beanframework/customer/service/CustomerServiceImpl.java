package com.beanframework.customer.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelService modelService;

	@Override
	public Customer findDtoAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			return null;
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Customer.ID, id);
		Customer customer = modelService.findOneDtoByProperties(properties, Customer.class);

		if (customer != null) {
			if (PasswordUtils.isMatch(password, customer.getPassword()) == false) {
				return null;
			}
		} else {
			return null;
		}

		for (UserGroup userGroup : customer.getUserGroups()) {
			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {

				if (Boolean.TRUE.equals(userAuthority.getEnabled())) {
					StringBuilder authority = new StringBuilder();
					authority.append(userAuthority.getUserPermission().getId());
					authority.append("_");
					authority.append(userAuthority.getUserRight().getId());

					customer.getAuthorities().add(new SimpleGrantedAuthority(authority.toString()));
				}

			}
		}

		return modelService.getDto(customer, Customer.class);
	}

	@Override
	public Customer create() throws Exception {
		return modelService.create(Customer.class);
	}

	@Override
	public Customer saveEntity(Customer model) throws BusinessException {
		return (Customer) modelService.saveEntity(model, Customer.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.ID, id);
			Customer model = modelService.findOneEntityByProperties(properties, Customer.class);
			modelService.deleteByEntity(model, Customer.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Customer getCurrentUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Customer customer = (Customer) auth.getPrincipal();
			return customer;
		} else {
			return null;
		}
	}
}
