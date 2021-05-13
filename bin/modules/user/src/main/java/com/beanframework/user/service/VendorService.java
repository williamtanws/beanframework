package com.beanframework.user.service;

import com.beanframework.user.domain.Vendor;

public interface VendorService {

  Vendor updatePrincipal(Vendor model);

  Vendor getCurrentUser() throws Exception;
}
