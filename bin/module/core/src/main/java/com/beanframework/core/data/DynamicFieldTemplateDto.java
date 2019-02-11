package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;

public class DynamicFieldTemplateDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5715432496604609750L;

	public static final String NAME = "name";

	private String name;

	private List<DynamicFieldSlotDto> dynamicFieldSlots = new ArrayList<DynamicFieldSlotDto>();

	private String[] tableDynamicFields;

	private String[] tableDynamicFieldSorts;

	private String[] tableSelectedDynamicFields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getTableDynamicFields() {
		return tableDynamicFields;
	}

	public void setTableDynamicFields(String[] tableDynamicFields) {
		this.tableDynamicFields = tableDynamicFields;
	}

	public String[] getTableDynamicFieldSorts() {
		return tableDynamicFieldSorts;
	}

	public void setTableDynamicFieldSorts(String[] tableDynamicFieldSorts) {
		this.tableDynamicFieldSorts = tableDynamicFieldSorts;
	}

	public String[] getTableSelectedDynamicFields() {
		return tableSelectedDynamicFields;
	}

	public void setTableSelectedDynamicFields(String[] tableSelectedDynamicFields) {
		this.tableSelectedDynamicFields = tableSelectedDynamicFields;
	}

	public List<DynamicFieldSlotDto> getDynamicFieldSlots() {
		return dynamicFieldSlots;
	}

	public void setDynamicFieldSlots(List<DynamicFieldSlotDto> dynamicFieldSlots) {
		this.dynamicFieldSlots = dynamicFieldSlots;
	}

}
