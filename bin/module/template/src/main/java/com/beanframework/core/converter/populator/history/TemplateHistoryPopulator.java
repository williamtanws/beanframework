package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.TemplateDto;
import com.beanframework.template.domain.Template;

@Component
public class TemplateHistoryPopulator extends AbstractPopulator<Template, TemplateDto> implements Populator<Template, TemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(TemplateHistoryPopulator.class);

	@Override
	public void populate(Template source, TemplateDto target) throws PopulatorException {
		convertCommonProperties(source, target);
	}

}
