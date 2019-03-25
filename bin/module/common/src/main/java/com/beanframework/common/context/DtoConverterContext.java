package com.beanframework.common.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoConverterContext {

	@Autowired
	private FetchContext fetchContext;

	public boolean isFetchable(Class<?> modelClass, String property) {
		return fetchContext.isFetchable(modelClass, property);
	}
}
