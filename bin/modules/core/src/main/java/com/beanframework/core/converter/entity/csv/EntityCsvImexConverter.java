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
import com.beanframework.core.csv.ImexCsv;
import com.beanframework.imex.domain.Imex;

public class EntityCsvImexConverter implements EntityCsvConverter<ImexCsv, Imex> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvImexConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Imex convert(ImexCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Imex.ID, source.getId());
				Imex prototype = modelService.findOneByProperties(properties, Imex.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Imex.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Imex convertToEntity(ImexCsv source, Imex prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (source.getType() != null)
				prototype.setType(source.getType());

			if (StringUtils.isNotBlank(source.getDirectory()))
				prototype.setDirectory(source.getDirectory());

			if (StringUtils.isNotBlank(source.getFileName()))
				prototype.setFileName(source.getFileName());

			if (StringUtils.isNotBlank(source.getQuery()))
				prototype.setQuery(source.getQuery().replace("%n", System.lineSeparator()));

			if (StringUtils.isNotBlank(source.getHeader()))
				prototype.setHeader(source.getHeader());

			if (StringUtils.isNotBlank(source.getSeperator()))
				prototype.setSeperator(source.getSeperator());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
