package com.beanframework.console.registry;

public abstract class ImportListener {

	public abstract void update() throws Exception;

	public abstract void remove() throws Exception;

	private String key;
	private String name;
	private int sort;
	private String description;
	public static final String SPLITTER = ";";
	public static final String EQUALS = "=";
	public static final String POSITIVE = "+";
	public static final String NEGATIVE = "-";
	public static final String UNDERSCORE = "_";

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}