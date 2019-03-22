package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.MediaCsv;
import com.beanframework.media.domain.Media;

@Component
public class EntityCsvMediaConverter implements EntityConverter<MediaCsv, Media> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvMediaConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Media convert(MediaCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Media.ID, source.getId());

				Media prototype = modelService.findOneEntityByProperties(properties, Media.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new Media());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public Media convert(MediaCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private Media convert(MediaCsv source, Media prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setFileName(StringUtils.stripToNull(source.getFileName()));
			prototype.setFileType(StringUtils.stripToNull(source.getFileType()));
			prototype.setUrl(StringUtils.stripToNull(source.getUrl()));
			prototype.setTitle(StringUtils.stripToNull(source.getTitle()));
			prototype.setCaption(StringUtils.stripToNull(source.getCaption()));
			prototype.setAltText(StringUtils.stripToNull(source.getAltText()));
			prototype.setDescription(StringUtils.stripToNull(source.getDescription()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
