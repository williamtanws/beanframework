package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.VendorDto;
import com.beanframework.vendor.domain.Vendor;

public class DtoVendorConverter extends AbstractDtoConverter<Vendor, VendorDto> implements DtoConverter<Vendor, VendorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoVendorConverter.class);

	@Override
	public VendorDto convert(Vendor source, DtoConverterContext context) throws ConverterException {
		return convert(source, new VendorDto(), context);
	}

	public List<VendorDto> convert(List<Vendor> sources, DtoConverterContext context) throws ConverterException {
		List<VendorDto> convertedList = new ArrayList<VendorDto>();
		for (Vendor source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private VendorDto convert(Vendor source, VendorDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			prototype.setPassword(source.getPassword());
			prototype.setAccountNonExpired(source.getAccountNonExpired());
			prototype.setAccountNonLocked(source.getAccountNonLocked());
			prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
			prototype.setEnabled(source.getEnabled());
			prototype.setName(source.getName());

			if (context.isFetchable(Vendor.class, Vendor.USER_GROUPS))
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
