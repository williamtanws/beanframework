package com.beanframework.core.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.beanframework.common.data.AbstractCsv;

public class UserGroupCsv extends AbstractCsv {

	private String name;
	private String userGroupIds;
	private String dynamicFieldSlotIds;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // name
				new Optional(new Trim()), // userGroupIds
				new Optional(new Trim()) // dynamicFieldSlotIds
		};

		return processors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "UserGroupCsv [id=" + id + ", name=" + name + ", userGroupIds=" + userGroupIds + ", dynamicFieldSlotIds=" + dynamicFieldSlotIds + "]";
	}
	
	

}
