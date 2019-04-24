package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.SiteCsv;

@Component
public class EntityCsvSiteConverter implements EntityCsvConverter<SiteCsv, Site> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvSiteConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Site convert(SiteCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Site.ID, source.getId());

				Site prototype = modelService.findOneEntityByProperties(properties, Site.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Site.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Site convertToEntity(SiteCsv source, Site prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			if (StringUtils.isNotBlank(source.getUrl()))
				prototype.setUrl(source.getUrl());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
