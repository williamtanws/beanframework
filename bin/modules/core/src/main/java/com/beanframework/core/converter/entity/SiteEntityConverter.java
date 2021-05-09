package com.beanframework.core.converter.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.SiteDto;

@Component
public class SiteEntityConverter implements EntityConverter<SiteDto, Site> {

	@Autowired
	private ModelService modelService;

	@Override
	public Site convert(SiteDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Site prototype = modelService.findOneByUuid(source.getUuid(), Site.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Site.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Site convertToEntity(SiteDto source, Site prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getUrl()), prototype.getUrl()) == Boolean.FALSE) {
			prototype.setUrl(StringUtils.stripToNull(source.getUrl()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}
