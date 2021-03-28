package com.beanframework.user.service;

import com.beanframework.internationalization.domain.Country;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;

public interface CompanyService {

	void removeContactPersonRel(User model) throws Exception;

	void removeResponsibleCompanyRel(Company model) throws Exception;

	void removeCountryRel(Country model) throws Exception;

	void removeAddressesRel(Address model) throws Exception;

	void removeShippingAddressRel(Address model) throws Exception;

	void removeUnloadingAddressRel(Address model) throws Exception;

	void removeBillingAddressRel(Address model) throws Exception;

	void removeContactAddressRel(Address model) throws Exception;
}
