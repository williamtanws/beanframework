package com.beanframework.core.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.AddressCsv;
import com.beanframework.user.domain.Address;

public class EntityCsvAddressConverter implements EntityCsvConverter<AddressCsv, Address> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvAddressConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Address convert(AddressCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Address.ID, source.getId());

				Address prototype = modelService.findOneByProperties(properties, Address.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Address.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Address convertToEntity(AddressCsv source, Address prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
