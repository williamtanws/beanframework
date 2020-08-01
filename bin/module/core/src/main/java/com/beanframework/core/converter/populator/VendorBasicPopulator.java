package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.VendorDto;
import com.beanframework.vendor.domain.Vendor;

@Component
public class VendorBasicPopulator extends AbstractPopulator<Vendor, VendorDto> implements Populator<Vendor, VendorDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(VendorBasicPopulator.class);

	@Override
	public void populate(Vendor source, VendorDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
