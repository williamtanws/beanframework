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
import com.beanframework.company.domain.Company;
import com.beanframework.core.csv.CompanyCsv;

public class EntityCsvCompanyConverter implements EntityCsvConverter<CompanyCsv, Company> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvCompanyConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Company convert(CompanyCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Company.ID, source.getId());

				Company prototype = modelService.findOneByProperties(properties, Company.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Company.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Company convertToEntity(CompanyCsv source, Company prototype) throws ConverterException {

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
