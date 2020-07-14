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
import com.beanframework.core.csv.MediaCsv;
import com.beanframework.media.domain.Media;


public class EntityCsvMediaConverter implements EntityCsvConverter<MediaCsv, Media> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvMediaConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Media convert(MediaCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Media.ID, source.getId());

				Media prototype = modelService.findOneByProperties(properties, Media.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Media.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Media convertToEntity(MediaCsv source, Media prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getFileName()))
				prototype.setFileName(source.getFileName());

			if (StringUtils.isNotBlank(source.getFileType()))
				prototype.setFileType(source.getFileType());

			if (StringUtils.isNotBlank(source.getUrl()))
				prototype.setUrl(source.getUrl());

			if (StringUtils.isNotBlank(source.getTitle()))
				prototype.setTitle(source.getTitle());

			if (StringUtils.isNotBlank(source.getCaption()))
				prototype.setCaption(source.getCaption());

			if (StringUtils.isNotBlank(source.getAltText()))
				prototype.setAltText(source.getAltText());

			if (StringUtils.isNotBlank(source.getDescription()))
				prototype.setDescription(source.getDescription());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
