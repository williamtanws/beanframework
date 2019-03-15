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

	private String[] tableDynamicFieldSlots;

	private String[] tableSelectedDynamicFieldSlots;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DynamicFieldSlotDto> getDynamicFieldSlots() {
		return dynamicFieldSlots;
	}

	public void setDynamicFieldSlots(List<DynamicFieldSlotDto> dynamicFieldSlots) {
		this.dynamicFieldSlots = dynamicFieldSlots;
	}

	public String[] getTableDynamicFieldSlots() {
		return tableDynamicFieldSlots;
	}

	public void setTableDynamicFieldSlots(String[] tableDynamicFieldSlots) {
		this.tableDynamicFieldSlots = tableDynamicFieldSlots;
	}

	public String[] getTableSelectedDynamicFieldSlots() {
		return tableSelectedDynamicFieldSlots;
	}

	public void setTableSelectedDynamicFieldSlots(String[] tableSelectedDynamicFieldSlots) {
		this.tableSelectedDynamicFieldSlots = tableSelectedDynamicFieldSlots;
	}

}
