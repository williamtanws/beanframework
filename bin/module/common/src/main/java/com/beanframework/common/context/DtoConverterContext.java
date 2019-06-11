package com.beanframework.common.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DtoConverterContext {

	private ConvertRelationType converModelType;

	private Set<UUID> convertedDtos = new HashSet<UUID>();

	private Map<String, Object> parameters = new HashMap<String, Object>();

	public DtoConverterContext() {
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

	public Set<UUID> getConvertedDtos() {
		return convertedDtos;
	}

	public void setConvertedDtos(Set<UUID> convertedDtos) {
		this.convertedDtos = convertedDtos;
	}

	@Override
	public String toString() {
		return "DtoConverterContext [converModelType=" + converModelType + ", convertedDtos=" + convertedDtos + ", parameters=" + parameters + "]";
	}

}
