package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;

public class CountryDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7536881992925185254L;
	private String name;
	private Boolean active;
	private List<RegionDto> regions = new ArrayList<RegionDto>();
	
	private String[] selectedRegions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<RegionDto> getRegions() {
		return regions;
	}

	public void setRegions(List<RegionDto> regions) {
		this.regions = regions;
	}

	public String[] getSelectedRegions() {
		return selectedRegions;
	}

	public void setSelectedRegions(String[] selectedRegions) {
		this.selectedRegions = selectedRegions;
	}

}
