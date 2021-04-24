package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.user.domain.Company;

public class DtoCompanyConverter extends AbstractDtoConverter<Company, CompanyDto> implements DtoConverter<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCompanyConverter.class);

	@Override
	public CompanyDto convert(Company source) throws ConverterException {
		return super.convert(source, new CompanyDto());
	}

}
