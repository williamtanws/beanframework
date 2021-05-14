package com.beanframework.common.converter;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.common.service.ModelService;

public abstract class AbstractDtoConverter<SOURCE extends GenericEntity, TARGET extends GenericDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(AbstractDtoConverter.class);

  @Autowired
  protected ModelService modelService;

  protected List<Populator<SOURCE, TARGET>> populators = new ArrayList<Populator<SOURCE, TARGET>>();

  public List<Populator<SOURCE, TARGET>> getPopulators() {
    return populators;
  }

  public void setPopulators(List<Populator<SOURCE, TARGET>> populators) {
    this.populators = populators;
  }

  public void addPopulator(Populator<SOURCE, TARGET> populator) {
    this.populators.add(populator);
  }

  public void removePopulator(Populator<SOURCE, TARGET> populator) {
    populators.removeIf(e -> e.getClass().getName().equals(populator.getClass().getName()));
  }

  public TARGET convert(SOURCE source, TARGET target) {
    try {
      populate(source, target);

      return target;
    } catch (PopulatorException e) {
      LOGGER.error(e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public List<TARGET> convert(List<SOURCE> sources, TARGET target) throws ConverterException {
    List<TARGET> convertedList = new ArrayList<TARGET>();
    for (SOURCE source : sources) {
      convertedList.add(convert(source, target));
    }
    return convertedList;
  }

  public void populate(SOURCE source, TARGET prototype) throws PopulatorException {
    for (Populator<?, ?> populatorMapping : getPopulators()) {
      @SuppressWarnings("unchecked")
      Populator<SOURCE, TARGET> populator = (Populator<SOURCE, TARGET>) populatorMapping;
      populator.populate(source, prototype);
    }
  }
}
