package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.language.domain.Language;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.LanguageDto;

public class EntityLanguageConverter implements EntityConverter<LanguageDto, Language> {

	@Autowired
	private ModelService modelService;

	@Override
	public Language convert(LanguageDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.UUID, source.getUuid());
				Language prototype = modelService.findOneEntityByProperties(properties, true,Language.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Language.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Language convert(LanguageDto source, Language prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.isBlank(source.getSort())) {
			if (prototype.getSort() != null) {
				prototype.setSort(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getSort() == null || prototype.getSort().equals(Integer.valueOf(source.getSort())) == false) {
				prototype.setSort(Integer.valueOf(source.getSort()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (prototype.getActive() == source.getActive() == false) {
			prototype.setActive(source.getActive());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
