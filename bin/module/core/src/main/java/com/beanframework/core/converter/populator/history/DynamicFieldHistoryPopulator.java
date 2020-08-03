package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class DynamicFieldHistoryPopulator extends AbstractPopulator<DynamicField, DynamicFieldDto> implements Populator<DynamicField, DynamicFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldHistoryPopulator.class);
	
	@Autowired
	private LanguageHistoryPopulator languageHistoryPopulator;
	
	@Autowired
	private EnumerationHistoryPopulator enumerationHistoryPopulator;

	@Override
	public void populate(DynamicField source, DynamicFieldDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setRequired(source.getRequired());
			target.setRule(source.getRule());
			target.setType(source.getType());
			target.setLabel(source.getLabel());
			target.setGrid(source.getGrid());
			target.setLanguage(modelService.getDto(source.getLanguage(), LanguageDto.class, new DtoConverterContext(languageHistoryPopulator)));
			target.setEnumerations(modelService.getDto(source.getEnumerations(), EnumerationDto.class, new DtoConverterContext(enumerationHistoryPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
