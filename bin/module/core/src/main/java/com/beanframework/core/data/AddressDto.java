package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class AddressDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5023918004039728891L;
	// General
	private String name;
	private String streetNumber;
	private String postalCode;
	private String town;
	private CountryDto country;

	// Additional Address Information
	private String company;
	private String phone1;
	private String phone2;
	private String mobilePhone;
	private String email;
	private String poBox;
	private String fax;

	// Additional Contact Information
	private String title;
	private String lastName;
	private String firstName;
	private String middleName;
	private String middleName2;
	private String department;
	private String building;
	private String apartment;
	private RegionDto region;
	private String district;
	private UserDto owner;

	private AddressDto shippingAddress;
	private AddressDto billingAddress;
	private AddressDto contactAddress;
	private AddressDto defaultPaymentAddress;
	private AddressDto defaultShipmentAddress;

	private String selectedCountry;
	private String selectedRegion;
	private String selectedOwner;
	private String selectedShippingAddress;
	private String selectedBillingAddress;
	private String selectedContactAddress;
	private String selectedDefaultPaymentAddress;
	private String selectedDefaultShipmentAddress;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMiddleName2() {
		return middleName2;
	}

	public void setMiddleName2(String middleName2) {
		this.middleName2 = middleName2;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public RegionDto getRegion() {
		return region;
	}

	public void setRegion(RegionDto region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
		this.owner = owner;
	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String getSelectedRegion() {
		return selectedRegion;
	}

	public void setSelectedRegion(String selectedRegion) {
		this.selectedRegion = selectedRegion;
	}

	public String getSelectedOwner() {
		return selectedOwner;
	}

	public void setSelectedOwner(String selectedOwner) {
		this.selectedOwner = selectedOwner;
	}

	public AddressDto getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressDto shippingAddress) {
		this.shippingAddress = shippingAddress;
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

	public AddressDto getDefaultPaymentAddress() {
		return defaultPaymentAddress;
	}

	public void setDefaultPaymentAddress(AddressDto defaultPaymentAddress) {
		this.defaultPaymentAddress = defaultPaymentAddress;
	}

	public AddressDto getDefaultShipmentAddress() {
		return defaultShipmentAddress;
	}

	public void setDefaultShipmentAddress(AddressDto defaultShipmentAddress) {
		this.defaultShipmentAddress = defaultShipmentAddress;
	}

	public String getSelectedShippingAddress() {
		return selectedShippingAddress;
	}

	public void setSelectedShippingAddress(String selectedShippingAddress) {
		this.selectedShippingAddress = selectedShippingAddress;
	}

	public String getSelectedBillingAddress() {
		return selectedBillingAddress;
	}

	public void setSelectedBillingAddress(String selectedBillingAddress) {
		this.selectedBillingAddress = selectedBillingAddress;
	}

	public String getSelectedContactAddress() {
		return selectedContactAddress;
	}

	public void setSelectedContactAddress(String selectedContactAddress) {
		this.selectedContactAddress = selectedContactAddress;
	}

	public String getSelectedDefaultPaymentAddress() {
		return selectedDefaultPaymentAddress;
	}

	public void setSelectedDefaultPaymentAddress(String selectedDefaultPaymentAddress) {
		this.selectedDefaultPaymentAddress = selectedDefaultPaymentAddress;
	}

	public String getSelectedDefaultShipmentAddress() {
		return selectedDefaultShipmentAddress;
	}

	public void setSelectedDefaultShipmentAddress(String selectedDefaultShipmentAddress) {
		this.selectedDefaultShipmentAddress = selectedDefaultShipmentAddress;
	}

}
