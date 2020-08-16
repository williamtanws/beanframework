package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.user.domain.Company;

public class DtoCompanyConverter extends AbstractDtoConverter<Company, CompanyDto> implements DtoConverter<Company, CompanyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCompanyConverter.class);

	@Override
	public CompanyDto convert(Company source, DtoConverterContext context) throws ConverterException {
		try {
			CompanyDto target = new CompanyDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<CompanyDto> convert(List<Company> sources, DtoConverterContext context) throws ConverterException {
		List<CompanyDto> convertedList = new ArrayList<CompanyDto>();
		try {
			for (Company source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
