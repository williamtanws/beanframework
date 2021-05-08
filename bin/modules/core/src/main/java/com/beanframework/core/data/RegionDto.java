package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RegionDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2749855287043565328L;
	private String name;
	private Boolean active;
	private CountryDto country;

	@JsonIgnore
	private String selectedCountryUuid;

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

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}

	public String getSelectedCountryUuid() {
		return selectedCountryUuid;
	}

	public void setSelectedCountryUuid(String selectedCountryUuid) {
		this.selectedCountryUuid = selectedCountryUuid;
	}
}
