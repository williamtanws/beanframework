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
import com.beanframework.core.csv.RegionCsv;
import com.beanframework.internationalization.domain.Region;

public class EntityCsvRegionConverter implements EntityCsvConverter<RegionCsv, Region> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvRegionConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Region convert(RegionCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Region.ID, source.getId());

				Region prototype = modelService.findOneByProperties(properties, Region.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Region.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Region convertToEntity(RegionCsv source, Region prototype) throws ConverterException {

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
