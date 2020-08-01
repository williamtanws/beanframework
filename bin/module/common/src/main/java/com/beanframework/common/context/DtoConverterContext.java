package com.beanframework.common.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.beanframework.common.converter.Populator;

public class DtoConverterContext {

//	private ConvertRelationType converModelType;

	private Set<UUID> convertedDtos = new HashSet<UUID>();

	private Map<String, Object> parameters = new HashMap<String, Object>();

	private List<Populator<?, ?>> populatorMappings = new ArrayList<Populator<?, ?>>();

	public DtoConverterContext() {
	}

//	public DtoConverterContext(ConvertRelationType converModelType) {
//		super();
//		this.converModelType = converModelType;
//	}
//
//	public DtoConverterContext(ConvertRelationType converModelType, Map<String, Object> parameters) {
//		super();
//		this.converModelType = converModelType;
//		this.parameters = parameters;
//	}

	public DtoConverterContext(Populator<?, ?>... populator) {
		for (Populator<?, ?> ppl : populator) {
			this.populatorMappings.add(ppl);
		}
	}

//	public ConvertRelationType getConverModelType() {
//		return converModelType;
//	}
//
//	public void setConverModelType(ConvertRelationType converModelType) {
//		this.converModelType = converModelType;
//	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public Set<UUID> getConvertedDtos() {
		return convertedDtos;
	}

	public void setConvertedDtos(Set<UUID> convertedDtos) {
		this.convertedDtos = convertedDtos;
	}

	public List<Populator<?, ?>> getPopulatorMappings() {
		return populatorMappings;
	}

	public void setPopulatorMappings(List<Populator<?, ?>> populatorMappings) {
		this.populatorMappings = populatorMappings;
	}

	@Override
	public String toString() {
		return "DtoConverterContext [populatorMappings=" + populatorMappings + ", convertedDtos=" + convertedDtos + ", parameters=" + parameters + "]";
	}
}
