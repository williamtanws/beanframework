package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;
import com.beanframework.user.LineOfBusinessType;

public class CompanyDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3976174059425847675L;
	private String name;
	private String description;
	private UserDto contactPerson;
	private CompanyDto responsibleCompany;
	private CountryDto country;
	private LineOfBusinessType lineOfBusiness;
	private Boolean buyer;
	private Boolean manufacturer;
	private Boolean supplier;
	private Boolean carrier;
	private String vatId;
	private String dunsId;
	private String ilnId;
	private String buyerSpecificId;
	private String supplierSpecificId;
	private List<AddressDto> addresses = new ArrayList<AddressDto>();
	private AddressDto shippingAddress;
	private AddressDto unloadingAddress;
	private AddressDto billingAddress;
	private AddressDto contactAddress;
	private List<UserDto> users = new ArrayList<UserDto>();

	private String selectedContactPersonUuid;
	private String selectedResponsibleCompanyUuid;
	private String selectedCountryUuid;
	private String[] selectedAddressUuids;
	private String selectedShippingAddressUuid;
	private String selectedUnloadingAddressUuid;
	private String selectedBillingAddressUuid;
	private String selectedContactAddressUuid;
	private String[] selectedUserUuids;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserDto getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(UserDto contactPerson) {
		this.contactPerson = contactPerson;
	}

	public CompanyDto getResponsibleCompany() {
		return responsibleCompany;
	}

	public void setResponsibleCompany(CompanyDto responsibleCompany) {
		this.responsibleCompany = responsibleCompany;
	}

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}

	public LineOfBusinessType getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(LineOfBusinessType lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public Boolean getBuyer() {
		return buyer;
	}

	public void setBuyer(Boolean buyer) {
		this.buyer = buyer;
	}

	public Boolean getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Boolean manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Boolean getSupplier() {
		return supplier;
	}

	public void setSupplier(Boolean supplier) {
		this.supplier = supplier;
	}

	public Boolean getCarrier() {
		return carrier;
	}

	public void setCarrier(Boolean carrier) {
		this.carrier = carrier;
	}

	public String getVatId() {
		return vatId;
	}

	public void setVatId(String vatId) {
		this.vatId = vatId;
	}

	public String getDunsId() {
		return dunsId;
	}

	public void setDunsId(String dunsId) {
		this.dunsId = dunsId;
	}

	public String getIlnId() {
		return ilnId;
	}

	public void setIlnId(String ilnId) {
		this.ilnId = ilnId;
	}

	public String getBuyerSpecificId() {
		return buyerSpecificId;
	}

	public void setBuyerSpecificId(String buyerSpecificId) {
		this.buyerSpecificId = buyerSpecificId;
	}

	public String getSupplierSpecificId() {
		return supplierSpecificId;
	}

	public void setSupplierSpecificId(String supplierSpecificId) {
		this.supplierSpecificId = supplierSpecificId;
	}

	public List<AddressDto> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDto> addresses) {
		this.addresses = addresses;
	}

	public AddressDto getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressDto shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public AddressDto getUnloadingAddress() {
		return unloadingAddress;
	}

	public void setUnloadingAddress(AddressDto unloadingAddress) {
		this.unloadingAddress = unloadingAddress;
	}

	public AddressDto getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressDto billingAddress) {
		this.billingAddress = billingAddress;
	}

	public AddressDto getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(AddressDto contactAddress) {
		this.contactAddress = contactAddress;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public String getSelectedContactPersonUuid() {
		return selectedContactPersonUuid;
	}

	public void setSelectedContactPersonUuid(String selectedContactPersonUuid) {
		this.selectedContactPersonUuid = selectedContactPersonUuid;
	}

	public String getSelectedResponsibleCompanyUuid() {
		return selectedResponsibleCompanyUuid;
	}

	public void setSelectedResponsibleCompanyUuid(String selectedResponsibleCompanyUuid) {
		this.selectedResponsibleCompanyUuid = selectedResponsibleCompanyUuid;
	}

	public String getSelectedCountryUuid() {
		return selectedCountryUuid;
	}

	public void setSelectedCountryUuid(String selectedCountryUuid) {
		this.selectedCountryUuid = selectedCountryUuid;
	}

	public String[] getSelectedAddressUuids() {
		return selectedAddressUuids;
	}

	public void setSelectedAddressUuids(String[] selectedAddressUuids) {
		this.selectedAddressUuids = selectedAddressUuids;
	}

	public String getSelectedShippingAddressUuid() {
		return selectedShippingAddressUuid;
	}

	public void setSelectedShippingAddressUuid(String selectedShippingAddressUuid) {
		this.selectedShippingAddressUuid = selectedShippingAddressUuid;
	}

	public String getSelectedUnloadingAddressUuid() {
		return selectedUnloadingAddressUuid;
	}

	public void setSelectedUnloadingAddressUuid(String selectedUnloadingAddressUuid) {
		this.selectedUnloadingAddressUuid = selectedUnloadingAddressUuid;
	}

	public String getSelectedBillingAddressUuid() {
		return selectedBillingAddressUuid;
	}

	public void setSelectedBillingAddressUuid(String selectedBillingAddressUuid) {
		this.selectedBillingAddressUuid = selectedBillingAddressUuid;
	}

	public String getSelectedContactAddressUuid() {
		return selectedContactAddressUuid;
	}

	public void setSelectedContactAddressUuid(String selectedContactAddressUuid) {
		this.selectedContactAddressUuid = selectedContactAddressUuid;
	}

	public String[] getSelectedUserUuids() {
		return selectedUserUuids;
	}

	public void setSelectedUserUuids(String[] selectedUserUuids) {
		this.selectedUserUuids = selectedUserUuids;
	}

}
