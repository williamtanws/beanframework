package com.beanframework.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.Vendor;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private ModelService modelService;

	@Override
	public Vendor getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Vendor principal = (Vendor) auth.getPrincipal();
			return modelService.findOneByUuid(principal.getUuid(), Vendor.class);
		} else {
			return null;
		}
	}

	@Override
	public Vendor updatePrincipal(Vendor model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Vendor principal = (Vendor) auth.getPrincipal();
		principal.setId(model.getId());
		principal.setName(model.getName());
		principal.setPassword(model.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return principal;
	}
}
