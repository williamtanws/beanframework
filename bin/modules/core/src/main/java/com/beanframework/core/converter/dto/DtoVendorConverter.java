package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.VendorDto;
import com.beanframework.user.domain.Vendor;

public class DtoVendorConverter extends AbstractDtoConverter<Vendor, VendorDto> implements DtoConverter<Vendor, VendorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoVendorConverter.class);

	@Override
	public VendorDto convert(Vendor source) throws ConverterException {
		return super.convert(source, new VendorDto());
	}

}
