package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.SiteDto;

public class EntitySiteConverter implements EntityConverter<SiteDto, Site> {

	@Autowired
	private ModelService modelService;

	@Override
	public Site convert(SiteDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Site.UUID, source.getUuid());
				Site prototype = modelService.findOneEntityByProperties(properties, Site.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}

			return convertDto(source, modelService.create(Site.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Site convertDto(SiteDto source, Site prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getUrl()), prototype.getUrl()) == false) {
			prototype.setUrl(StringUtils.stripToNull(source.getUrl()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
