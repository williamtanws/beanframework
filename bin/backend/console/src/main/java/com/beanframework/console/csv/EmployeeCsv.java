package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class EmployeeCsv extends AbstractCsv {

	private String name;
	private String password;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private Boolean enabled;
	private String userGroupIds;
	private String dynamicFieldSlotIds;
	private String profilePicture;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // name
				new Optional(new Trim()), // password
				new Optional(new Trim(new ParseBool())), // accountNonExpired
				new Optional(new Trim(new ParseBool())), // accountNonLocked
				new Optional(new Trim(new ParseBool())), // credentialsNonExpired
				new Optional(new Trim(new ParseBool())), // enabled
				new Optional(new Trim()), // userGroupIds
				new Optional(new Trim()), // dynamicFieldSlotIds
				new Optional(new Trim()) // profilePicture
		};

		return processors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(String userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public String getDynamicFieldSlotIds() {
		return dynamicFieldSlotIds;
	}

	public void setDynamicFieldSlotIds(String dynamicFieldSlotIds) {
		this.dynamicFieldSlotIds = dynamicFieldSlotIds;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

}
