package com.beanframework.vendor.service;

import com.beanframework.vendor.domain.Vendor;

public interface VendorService {

	Vendor updatePrincipal(Vendor model);

	Vendor getCurrentUser() throws Exception;
}
