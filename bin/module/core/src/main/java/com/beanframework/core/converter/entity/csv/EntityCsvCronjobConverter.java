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
import com.beanframework.core.csv.CronjobCsv;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.imex.registry.ImportListener;

public class EntityCsvCronjobConverter implements EntityCsvConverter<CronjobCsv, Cronjob> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvCronjobConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Cronjob convert(CronjobCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Cronjob.ID, source.getId());

				Cronjob prototype = modelService.findOneByProperties(properties, Cronjob.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Cronjob.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Cronjob convertToEntity(CronjobCsv source, Cronjob prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getJobClass()))
				prototype.setJobClass(source.getJobClass());

			if (StringUtils.isNotBlank(source.getJobGroup()))
				prototype.setJobGroup(source.getJobGroup());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			if (StringUtils.isNotBlank(source.getDescription()))
				prototype.setDescription(source.getDescription());

			if (StringUtils.isNotBlank(source.getCronExpression()))
				prototype.setCronExpression(source.getCronExpression());

			if (source.getStartup() != null)
				prototype.setStartup(source.getStartup());

			// Cronjob Data
			if (StringUtils.isNotBlank(source.getParameters())) {
				String[] cronjobDataList = source.getParameters().split(ImportListener.SPLITTER);

				for (String cronjobDataString : cronjobDataList) {
					String name = cronjobDataString.split(ImportListener.EQUALS)[0];
					String value = cronjobDataString.split(ImportListener.EQUALS)[1];

					prototype.getParameters().put(name, value);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
