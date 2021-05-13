package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.internationalization.domain.Language;

@Component
public class LanguagePopulator extends AbstractPopulator<Language, LanguageDto>
    implements Populator<Language, LanguageDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(LanguagePopulator.class);

  @Override
  public void populate(Language source, LanguageDto target) throws PopulatorException {
    populateGeneric(source, target);
    target.setName(source.getName());
    target.setSort(source.getSort());
    target.setActive(source.getActive());
  }

}
