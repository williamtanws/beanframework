package com.beanframework.user.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.User;

public interface AddressService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	void removeOwnerRel(User user) throws Exception;

	void removeCountryRel(Country country) throws Exception;

	void removeRegionRel(Region region) throws Exception;

	void removeShippingAddressRel(Address address) throws Exception;

	void removeBillingAddressRel(Address address) throws Exception;

	void removeContactAddressRel(Address address) throws Exception;

	void removeDefaultPaymentAddressRel(Address address) throws Exception;

	void removeDefaultShipmentAddressRel(Address address) throws Exception;
}
