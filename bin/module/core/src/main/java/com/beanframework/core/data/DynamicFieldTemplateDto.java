package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldTemplateDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5715432496604609750L;

	public static final String NAME = "name";

	private String name;

	private List<DynamicField> dynamicFields = new ArrayList<DynamicField>();

	private String[] tableDynamicFields;

	private String[] tableSelectedDynamicFields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DynamicField> getDynamicFields() {
		return dynamicFields;
	}

	public void setDynamicFields(List<DynamicField> dynamicFields) {
		this.dynamicFields = dynamicFields;
	}

	public String[] getTableDynamicFields() {
		return tableDynamicFields;
	}

	public void setTableDynamicFields(String[] tableDynamicFields) {
		this.tableDynamicFields = tableDynamicFields;
	}

	public String[] getTableSelectedDynamicFields() {
		return tableSelectedDynamicFields;
	}

	public void setTableSelectedDynamicFields(String[] tableSelectedDynamicFields) {
		this.tableSelectedDynamicFields = tableSelectedDynamicFields;
	}

}
