package com.beanframework.vendor.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.vendor.domain.Vendor;

public class VendorListLoadInterceptor extends AbstractLoadInterceptor<Vendor> {

	@Override
	public Vendor onLoad(Vendor model, InterceptorContext context) throws InterceptorException {
		Vendor prototype = new Vendor();
		loadCommonProperties(model, prototype, context);
		prototype.setType(model.getType());
		prototype.setPassword(model.getPassword());
		prototype.setAccountNonExpired(model.getAccountNonExpired());
		prototype.setAccountNonLocked(model.getAccountNonLocked());
		prototype.setCredentialsNonExpired(model.getCredentialsNonExpired());
		prototype.setEnabled(model.getEnabled());
		prototype.setName(model.getName());

		return prototype;
	}

}
