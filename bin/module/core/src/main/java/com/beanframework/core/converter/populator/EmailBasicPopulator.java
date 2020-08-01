package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.domain.Email;

@Component
public class EmailBasicPopulator extends AbstractPopulator<Email, EmailDto> implements Populator<Email, EmailDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmailBasicPopulator.class);

	@Override
	public void populate(Email source, EmailDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
	}

}
