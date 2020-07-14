package com.beanframework.address.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.address.AddressConstants;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.User;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = AddressConstants.Table.ADDRESS)
public class Address extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7791727441562748178L;

	public static final String STREET_NAME = "streetName";
	public static final String STREET_NUMBER = "streetNumber";
	public static final String POSTAL_CODE = "postalCode";
	public static final String TOWN = "town";
	public static final String COUNTRY = "country";

	// Additional Address Information
	public static final String COMPANY = "company";
	public static final String PHONE1 = "phone1";
	public static final String PHONE2 = "phone2";
	public static final String MOBILE_PHONE = "mobilePhone";
	public static final String EMAIL = "email";
	public static final String PO_BOX = "poBox";
	public static final String FAX = "fax";

	// Additional Contact Information
	public static final String TITLE = "title";
	public static final String LAST_NAME = "lastName";
	public static final String FIRST_NAME = "firstName";
	public static final String MIDDLE_NAME = "middleName";
	public static final String MIDDLE_NAME2 = "middleName2";
	public static final String DEPARTMENT = "department";
	public static final String BUILDING = "building";
	public static final String APARTMENT = "apartment";
	public static final String REGION = "region";
	public static final String DISTRICT = "district";

	public static final String SHIPPING_ADDRESS = "shippingAddress";
	public static final String BILLING_ADDRESS = "billingAddress";
	public static final String CONTACT_ADDRESS = "contactAddress";
	public static final String DEFAULT_PAYMENT_ADDRESS = "defaultPaymentAddress";
	public static final String DEFAULT_SHIPMENT_ADDRESS = "defaultShipmentAddress";

	public static final String OWNER = "owner";

	// General
	@Audited(withModifiedFlag = true)
	private String streetName;
	@Audited(withModifiedFlag = true)
	private String streetNumber;
	@Audited(withModifiedFlag = true)
	private String postalCode;
	@Audited(withModifiedFlag = true)
	private String town;

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_uuid")
	private Country country;

	// Additional Address Information
	@Audited(withModifiedFlag = true)
	private String company;
	@Audited(withModifiedFlag = true)
	private String phone1;
	@Audited(withModifiedFlag = true)
	private String phone2;
	@Audited(withModifiedFlag = true)
	private String mobilePhone;
	@Audited(withModifiedFlag = true)
	private String email;
	@Audited(withModifiedFlag = true)
	private String poBox;
	@Audited(withModifiedFlag = true)
	private String fax;

	// Additional Contact Information
	@Audited(withModifiedFlag = true)
	private String title;
	@Audited(withModifiedFlag = true)
	private String lastName;
	@Audited(withModifiedFlag = true)
	private String firstName;
	@Audited(withModifiedFlag = true)
	private String middleName;
	@Audited(withModifiedFlag = true)
	private String middleName2;
	@Audited(withModifiedFlag = true)
	private String department;
	@Audited(withModifiedFlag = true)
	private String building;
	@Audited(withModifiedFlag = true)
	private String apartment;

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region_uuid")
	private Region region;

	@Audited(withModifiedFlag = true)
	private String district;

	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_uuid")
	private User owner;

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
