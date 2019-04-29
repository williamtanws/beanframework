package com.beanframework.common.context;

import java.util.HashMap;
import java.util.Map;

public class DtoConverterContext {

	private ConvertRelationType converModelType;

	private Map<String, Object> parameters = new HashMap<String, Object>();

	public DtoConverterContext()
	{
	}

	public DtoConverterContext(ConvertRelationType converModelType) {
		super();
		this.converModelType = converModelType;
	}
	
	public DtoConverterContext(ConvertRelationType converModelType, Map<String, Object> parameters) {
		super();
		this.converModelType = converModelType;
		this.parameters = parameters;
	}


	public ConvertRelationType getConverModelType() {
		return converModelType;
	}

	public void setConverModelType(ConvertRelationType converModelType) {
		this.converModelType = converModelType;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

}
