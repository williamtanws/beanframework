package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.ImexDto;
import com.beanframework.imex.domain.Imex;

public class EntityImexConverter implements EntityConverter<ImexDto, Imex> {

	@Autowired
	private ModelService modelService;

	@Override
	public Imex convert(ImexDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Imex.UUID, source.getUuid());
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

	private Imex convertToEntity(ImexDto source, Imex prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (prototype.getType() == source.getType() == Boolean.FALSE) {
			prototype.setType(source.getType());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getDirectory()), prototype.getDirectory()) == Boolean.FALSE) {
			prototype.setDirectory(StringUtils.stripToNull(source.getDirectory()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getFileName()), prototype.getFileName()) == Boolean.FALSE) {
			prototype.setFileName(StringUtils.stripToNull(source.getFileName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getQuery()), prototype.getQuery()) == Boolean.FALSE) {
			prototype.setQuery(StringUtils.stripToNull(source.getQuery()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getHeader()), prototype.getHeader()) == Boolean.FALSE) {
			prototype.setHeader(StringUtils.stripToNull(source.getHeader()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getSeperator()), prototype.getSeperator()) == Boolean.FALSE) {
			prototype.setSeperator(StringUtils.stripToNull(source.getSeperator()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
