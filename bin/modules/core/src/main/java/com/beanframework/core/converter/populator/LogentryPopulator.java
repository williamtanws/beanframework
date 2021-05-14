package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.LogentryDto;
import com.beanframework.logentry.domain.Logentry;

@Component
public class LogentryPopulator extends AbstractPopulator<Logentry, LogentryDto>
    implements Populator<Logentry, LogentryDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(LogentryPopulator.class);

  @Override
  public void populate(Logentry source, LogentryDto target) throws PopulatorException {
    populateGeneric(source, target);
    target.setType(source.getType());
    target.setMessage(source.getMessage());
  }

}
