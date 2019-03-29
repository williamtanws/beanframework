package com.beanframework.common.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FetchProperties {

	private static final Map<String, Set<String>> instance = new HashMap<String, Set<String>>();

	protected FetchProperties() {
	}

	// Runtime initialization
	// By defualt ThreadSafe
	public static Map<String, Set<String>> getInstance() {
		return instance;
	}
}
