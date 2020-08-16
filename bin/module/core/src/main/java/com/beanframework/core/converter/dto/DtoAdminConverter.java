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
import com.beanframework.core.data.AdminDto;
import com.beanframework.user.domain.Admin;

public class DtoAdminConverter extends AbstractDtoConverter<Admin, AdminDto> implements DtoConverter<Admin, AdminDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAdminConverter.class);

	@Override
	public AdminDto convert(Admin source, DtoConverterContext context) throws ConverterException {
		try {
			AdminDto target = new AdminDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<AdminDto> convert(List<Admin> sources, DtoConverterContext context) throws ConverterException {
		List<AdminDto> convertedList = new ArrayList<AdminDto>();
		try {
			for (Admin source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

}
