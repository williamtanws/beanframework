package com.beanframework.user.service;

import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.User;

public interface AddressService {

	void removeOwnerRel(User user) throws Exception;

	void removeCountryRel(Country country) throws Exception;

	void removeRegionRel(Region region) throws Exception;

	void removeShippingAddressRel(Address address) throws Exception;

	void removeBillingAddressRel(Address address) throws Exception;

	void removeContactAddressRel(Address address) throws Exception;

	void removeDefaultPaymentAddressRel(Address address) throws Exception;

	void removeDefaultShipmentAddressRel(Address address) throws Exception;
}
