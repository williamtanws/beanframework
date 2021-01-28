package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.TemplateDto;
import com.beanframework.template.domain.Template;

@Component
public class TemplatePopulator extends AbstractPopulator<Template, TemplateDto> implements Populator<Template, TemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(TemplatePopulator.class);

	@Override
	public void populate(Template source, TemplateDto target) throws PopulatorException {
		convertCommonProperties(source, target);
	}

}
