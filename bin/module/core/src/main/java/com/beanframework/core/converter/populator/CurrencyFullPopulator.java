package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.internationalization.domain.Currency;

@Component
public class CurrencyFullPopulator extends AbstractPopulator<Currency, CurrencyDto> implements Populator<Currency, CurrencyDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CurrencyFullPopulator.class);

	@Override
	public void populate(Currency source, CurrencyDto target) throws PopulatorException {
		convertCommonProperties(source, target);
	}

}
