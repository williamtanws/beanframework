package com.beanframework.core.converter.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.internationalization.domain.Language;

@Component
public class LanguageEntityConverter implements EntityConverter<LanguageDto, Language> {

	@Autowired
	private ModelService modelService;

	@Override
	public Language convert(LanguageDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Language prototype = modelService.findOneByUuid(source.getUuid(), Language.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Language.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Language convertToEntity(LanguageDto source, Language prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getSort() == null) {
			if (prototype.getSort() != null) {
				prototype.setSort(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getSort() == null || prototype.getSort() == source.getSort() == Boolean.FALSE) {
				prototype.setSort(source.getSort());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (prototype.getActive() == source.getActive() == Boolean.FALSE) {
			prototype.setActive(source.getActive());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
