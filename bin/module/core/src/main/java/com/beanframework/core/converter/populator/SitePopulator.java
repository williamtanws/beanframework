package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.SiteDto;


public class SitePopulator extends AbstractPopulator<Site, SiteDto> implements Populator<Site, SiteDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(SitePopulator.class);

	@Override
	public void populate(Site source, SiteDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setUrl(source.getUrl());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
