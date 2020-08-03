package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.EmailDto;
import com.beanframework.email.domain.Email;

@Component
public class EmailHistoryPopulator extends AbstractPopulator<Email, EmailDto> implements Populator<Email, EmailDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmailHistoryPopulator.class);

	@Override
	public void populate(Email source, EmailDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
	}

}
