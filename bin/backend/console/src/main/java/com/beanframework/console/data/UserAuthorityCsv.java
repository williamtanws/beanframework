package com.beanframework.console.data;

public class UserAuthorityCsv {

	private String userGroupId;
	private String userPermissionId;
	private String create;
	private String read;
	private String update;
	private String delete;

	public UserAuthorityCsv() {
		super();
	}

	public UserAuthorityCsv(String userGroupId, String userPermissionId, String create, String read,
			String update, String delete) {
		super();
		this.userGroupId = userGroupId;
		this.userPermissionId = userPermissionId;
		this.create = create;
		this.read = read;
		this.update = update;
		this.delete = delete;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserPermissionId() {
		return userPermissionId;
	}

	public void setUserPermissionId(String userPermissionId) {
		this.userPermissionId = userPermissionId;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

}
