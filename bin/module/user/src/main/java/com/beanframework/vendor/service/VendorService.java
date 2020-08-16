package com.beanframework.vendor.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.vendor.domain.Vendor;

public interface VendorService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	Vendor updatePrincipal(Vendor model);

	Vendor getCurrentUser() throws Exception;
}
