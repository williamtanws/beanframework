package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.cms.domain.Site;
import com.beanframework.cms.service.SiteService;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.SiteCsv;

@Component
public class EntityCsvSiteConverter implements EntityConverter<SiteCsv, Site> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvSiteConverter.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private SiteService siteService;

	@Override
	public Site convert(SiteCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Site.ID, source.getId());

				Site prototype = siteService.findOneEntityByProperties(properties);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Site.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public Site convert(SiteCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private Site convertToEntity(SiteCsv source, Site prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setUrl(StringUtils.stripToNull(source.getUrl()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
