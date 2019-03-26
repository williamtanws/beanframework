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
import com.beanframework.media.service.MediaService;

@Component
public class EntityCsvMediaConverter implements EntityConverter<MediaCsv, Media> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvMediaConverter.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private MediaService mediaService;


	@Override
	public Media convert(MediaCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Media.ID, source.getId());

				Media prototype = mediaService.findOneEntityByProperties(properties);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Media.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public Media convert(MediaCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private Media convertToEntity(MediaCsv source, Media prototype) throws ConverterException {

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
