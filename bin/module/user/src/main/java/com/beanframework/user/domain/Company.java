package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.CompanyConstants;
import com.beanframework.user.LineOfBusinessType;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CompanyConstants.Table.COMPANY)
public class Company extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7791727441562748178L;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String CONTACT_PERSON = "contactPerson";
	public static final String RESPONSIBLE_COMPANY = "responsibleCompany";
	public static final String COUNTRY = "country";
	public static final String LINE_OF_BUSINESS = "lineOfBusiness";
	public static final String BUYER = "buyer";
	public static final String MANUFACTURER = "manufacturer";
	public static final String SUPPLIER = "supplier";
	public static final String CARRIER = "carrier";
	public static final String VATID = "vatId";
	public static final String DUNSID = "dunsId";
	public static final String ILNID = "ilnId";
	public static final String BUYER_SPECIFIC_ID = "buyerSpecificId";
	public static final String SUPPLIER_SPECIFIC_ID = "supplierSpecificId";
	public static final String ADDRESS = "addresses";
	public static final String SHIPPING_ADDRESS = "shippingAddress";
	public static final String UNLOADING_ADDRESS = "unloadingAddress";
	public static final String BILLING_ADDRESS = "billingAddress";
	public static final String CONTACT_ADDRESS = "contactAddress";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private String description;

	@Audited(withModifiedFlag = true)
	@Column(name="contactperson_uuid")
	private UUID contactPerson;

	@Audited(withModifiedFlag = true)
	@Column(name="company_uuid")
	private UUID responsibleCompany;

	@Audited(withModifiedFlag = true)
	@Column(name="country_uuid")
	private UUID country;

	@Audited(withModifiedFlag = true)
	@Enumerated(EnumType.STRING)
	private LineOfBusinessType lineOfBusiness;

	@Audited(withModifiedFlag = true)
	private Boolean buyer;

	@Audited(withModifiedFlag = true)
	private Boolean manufacturer;

	@Audited(withModifiedFlag = true)
	private Boolean supplier;

	@Audited(withModifiedFlag = true)
	private Boolean carrier;

	@Audited(withModifiedFlag = true)
	private String vatId;

	@Audited(withModifiedFlag = true)
	private String dunsId;

	@Audited(withModifiedFlag = true)
	private String ilnId;

	@Audited(withModifiedFlag = true)
	private String buyerSpecificId;

	@Audited(withModifiedFlag = true)
	private String supplierSpecificId;

	@Audited(withModifiedFlag = true)
	@ElementCollection
	@CollectionTable(name = CompanyConstants.Table.COMPANY_ADDRESS_REL, joinColumns = @JoinColumn(name = "company_uuid"))
	@Column(name="address_uuid")
	private List<UUID> addresses = new ArrayList<UUID>();

	@Audited(withModifiedFlag = true)
	@Column(name="shipping_address_uuid")
	private UUID shippingAddress;

	@Audited(withModifiedFlag = true)
	@Column(name="unloading_address_uuid")
	private UUID unloadingAddress;

	@Audited(withModifiedFlag = true)
	@Column(name="billing_address_uuid")
	private UUID billingAddress;

	@Audited(withModifiedFlag = true)
	@Column(name="contact_address_uuid")
	private UUID contactAddress;

	@Audited(withModifiedFlag = true)
	@ElementCollection
	@CollectionTable(name = CompanyConstants.Table.COMPANY_ADDRESS_REL, joinColumns = @JoinColumn(name = "company_uuid"))
	@Column(name="user_uuid")
	private List<UUID> users = new ArrayList<UUID>();

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

	public UUID getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(UUID contactPerson) {
		this.contactPerson = contactPerson;
	}

	public UUID getResponsibleCompany() {
		return responsibleCompany;
	}

	public void setResponsibleCompany(UUID responsibleCompany) {
		this.responsibleCompany = responsibleCompany;
	}

	public UUID getCountry() {
		return country;
	}

	public void setCountry(UUID country) {
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

	public List<UUID> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<UUID> addresses) {
		this.addresses = addresses;
	}

	public UUID getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(UUID shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public UUID getUnloadingAddress() {
		return unloadingAddress;
	}

	public void setUnloadingAddress(UUID unloadingAddress) {
		this.unloadingAddress = unloadingAddress;
	}

	public UUID getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(UUID billingAddress) {
		this.billingAddress = billingAddress;
	}

	public UUID getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(UUID contactAddress) {
		this.contactAddress = contactAddress;
	}

	public List<UUID> getUsers() {
		return users;
	}

	public void setUsers(List<UUID> users) {
		this.users = users;
	}

}
