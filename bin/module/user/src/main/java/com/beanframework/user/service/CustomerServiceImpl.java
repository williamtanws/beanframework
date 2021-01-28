package com.beanframework.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelService modelService;

	@Override
	public Customer getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Customer principal = (Customer) auth.getPrincipal();
			return modelService.findOneByUuid(principal.getUuid(), Customer.class);
		} else {
			return null;
		}
	}

	@Override
	public Customer updatePrincipal(Customer model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer principal = (Customer) auth.getPrincipal();
		principal.setId(model.getId());
		principal.setName(model.getName());
		principal.setPassword(model.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return principal;
	}
}
