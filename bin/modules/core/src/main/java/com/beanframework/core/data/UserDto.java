package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.GenericDto;

public class UserDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7847327425785013603L;
	public static final String PASSWORD = "password";
	public static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
	public static final String ACCOUNT_NON_LOCKED = "accountNonLocked";
	public static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
	public static final String ENABLED = "enabled";
	public static final String USER_GROUPS = "userGroups";
	public static final String COMPANIES = "companies";
	public static final String ADDRESSES = "addresses";
	public static final String USER_ROLES = "userRoles";
	public static final String FIELDS = "fields";
	public static final String NAME = "name";

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
	
	private MultipartFile profilePicture;

	private String[] selectedUserGroupUuids;
	private String[] selectedCompanyUuids;
	private String[] selectedAddressUuids;

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

	public MultipartFile getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String[] getSelectedUserGroupUuids() {
		return selectedUserGroupUuids;
	}

	public void setSelectedUserGroupUuids(String[] selectedUserGroups) {
		this.selectedUserGroupUuids = selectedUserGroups;
	}

	public String[] getSelectedCompanyUuids() {
		return selectedCompanyUuids;
	}

	public void setSelectedCompanyUuids(String[] selectedCompanies) {
		this.selectedCompanyUuids = selectedCompanies;
	}

	public String[] getSelectedAddressUuids() {
		return selectedAddressUuids;
	}

	public void setSelectedAddressUuids(String[] selectedAddresses) {
		this.selectedAddressUuids = selectedAddresses;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
