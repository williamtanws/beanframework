package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.GenericDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7847327425785013603L;

	private String type;
	private String password;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private Boolean enabled;
	private String name;
	private List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();
	private List<CompanyDto> companies = new ArrayList<CompanyDto>();
	private List<AddressDto> addresses = new ArrayList<AddressDto>();
	private List<UserFieldDto> fields = new ArrayList<UserFieldDto>();
	private Map<String, String> parameters = new HashMap<String, String>();

	@JsonIgnore
	private String[] selectedParameterKeys;
	@JsonIgnore
	private String[] selectedParameterValues;
	@JsonIgnore
	private String[] selectedUserGroupUuids;
	@JsonIgnore
	private String[] selectedCompanyUuids;
	@JsonIgnore
	private String[] selectedAddressUuids;

	private MultipartFile profilePicture;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserGroupDto> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroupDto> userGroups) {
		this.userGroups = userGroups;
	}

	public List<CompanyDto> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyDto> companies) {
		this.companies = companies;
	}

	public List<AddressDto> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDto> addresses) {
		this.addresses = addresses;
	}

	public List<UserFieldDto> getFields() {
		return fields;
	}

	public void setFields(List<UserFieldDto> fields) {
		this.fields = fields;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String[] getSelectedParameterKeys() {
		return selectedParameterKeys;
	}

	public void setSelectedParameterKeys(String[] selectedParameterKeys) {
		this.selectedParameterKeys = selectedParameterKeys;
	}

	public String[] getSelectedParameterValues() {
		return selectedParameterValues;
	}

	public void setSelectedParameterValues(String[] selectedParameterValues) {
		this.selectedParameterValues = selectedParameterValues;
	}

	public String[] getSelectedUserGroupUuids() {
		return selectedUserGroupUuids;
	}

	public void setSelectedUserGroupUuids(String[] selectedUserGroupUuids) {
		this.selectedUserGroupUuids = selectedUserGroupUuids;
	}

	public String[] getSelectedCompanyUuids() {
		return selectedCompanyUuids;
	}

	public void setSelectedCompanyUuids(String[] selectedCompanyUuids) {
		this.selectedCompanyUuids = selectedCompanyUuids;
	}

	public String[] getSelectedAddressUuids() {
		return selectedAddressUuids;
	}

	public void setSelectedAddressUuids(String[] selectedAddressUuids) {
		this.selectedAddressUuids = selectedAddressUuids;
	}

	public MultipartFile getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
	}

}
