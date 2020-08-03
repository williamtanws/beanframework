package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.internationalization.domain.Language;

@Component
public class LanguageHistoryPopulator extends AbstractPopulator<Language, LanguageDto> implements Populator<Language, LanguageDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(LanguageHistoryPopulator.class);

	@Override
	public void populate(Language source, LanguageDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setSort(source.getSort());
		target.setActive(source.getActive());
	}

}
