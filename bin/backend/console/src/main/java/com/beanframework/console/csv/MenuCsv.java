package com.beanframework.console.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class MenuCsv extends AbstractCsv {

	private String name;
	private Integer sort;
	private String icon;
	private String path;
	private String target;
	private Boolean enabled;
	private String parent;
	private String userGroupIds;
	private String dynamicFieldSlotIds;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new NotNull(new Trim()), // ID
				new Optional(new Trim()), // name
				new Optional(new Trim(new ParseInt())), // sort
				new Optional(new Trim()), // icon
				new Optional(new Trim()), // path
				new Optional(new Trim()), // target
				new Optional(new Trim(new ParseBool())), // enabled
				new Optional(new Trim()), // parent
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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
}
