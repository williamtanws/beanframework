package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;
import com.beanframework.company.BusinessLineType;

public class CompanyDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3976174059425847675L;
	private String name;
	private String description;
	private UserDto contactPerson;
	private CompanyDto responsibleCompany;
	private CountryDto countryDto;
	private BusinessLineType lineOfBusiness;
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

	private String selectedResponsibleCompany;
	private String selectedCountry;
	private String[] selectedAddresses;
	private String selectedShippingAddress;
	private String selectedUnloadingAddress;
	private String selectedBillingAddress;
	private String selectedContactAddress;

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

	public CountryDto getCountryDto() {
		return countryDto;
	}

	public void setCountryDto(CountryDto countryDto) {
		this.countryDto = countryDto;
	}

	public BusinessLineType getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(BusinessLineType lineOfBusiness) {
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

	public String getSelectedResponsibleCompany() {
		return selectedResponsibleCompany;
	}

	public void setSelectedResponsibleCompany(String selectedResponsibleCompany) {
		this.selectedResponsibleCompany = selectedResponsibleCompany;
	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String[] getSelectedAddresses() {
		return selectedAddresses;
	}

	public void setSelectedAddresses(String[] selectedAddresses) {
		this.selectedAddresses = selectedAddresses;
	}

	public String getSelectedShippingAddress() {
		return selectedShippingAddress;
	}

	public void setSelectedShippingAddress(String selectedShippingAddress) {
		this.selectedShippingAddress = selectedShippingAddress;
	}

	public String getSelectedUnloadingAddress() {
		return selectedUnloadingAddress;
	}

	public void setSelectedUnloadingAddress(String selectedUnloadingAddress) {
		this.selectedUnloadingAddress = selectedUnloadingAddress;
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

}
