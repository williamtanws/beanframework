package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserFieldDto;
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

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertBasic(source, prototype, context);
			}
			prototype.setName(source.getName());

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(Vendor source, VendorDto prototype, DtoConverterContext context) throws Exception {

		prototype.setPassword(source.getPassword());
		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, new DtoConverterContext()));

		prototype.setFields(modelService.getDto(source.getFields(), UserFieldDto.class, new DtoConverterContext(ConvertRelationType.ALL)));
		if (prototype.getFields() != null)
			Collections.sort(prototype.getFields(), new Comparator<UserFieldDto>() {
				@Override
				public int compare(UserFieldDto o1, UserFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});

	}

	private void convertBasic(Vendor source, VendorDto prototype, DtoConverterContext context) throws Exception {
	}

}
