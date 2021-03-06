package com.beanframework.core.converter.populator;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.internationalization.domain.Language;

@Component
public class DynamicFieldPopulator extends AbstractPopulator<DynamicField, DynamicFieldDto>
    implements Populator<DynamicField, DynamicFieldDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldPopulator.class);

  @Override
  public void populate(DynamicField source, DynamicFieldDto target) throws PopulatorException {
    try {
      populateGeneric(source, target);
      target.setName(source.getName());
      target.setRequired(source.getRequired());
      target.setRule(source.getRule());
      target.setType(source.getType());
      target.setLabel(source.getLabel());
      target.setGrid(source.getGrid());

      target.setLanguage(populateLanguage(source.getLanguage()));

      for (UUID uuid : source.getEnumerations()) {
        target.getEnumerations().add(populateEnumeration(uuid));
      }

    } catch (Exception e) {
      throw new PopulatorException(e.getMessage(), e);
    }
  }

  public LanguageDto populateLanguage(UUID uuid) throws PopulatorException {
    if (uuid == null)
      return null;

    try {
      Language source = modelService.findOneByUuid(uuid, Language.class);
      if (source == null) {
        return null;
      }

      LanguageDto target = new LanguageDto();
      target.setUuid(source.getUuid());
      target.setId(source.getId());
      target.setCreatedDate(source.getCreatedDate());
      target.setLastModifiedDate(source.getLastModifiedDate());
      target.setCreatedBy(populateAuditor(source.getCreatedBy()));
      target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

      target.setName(source.getName());
      target.setSort(source.getSort());
      target.setActive(source.getActive());

      return target;
    } catch (Exception e) {
      throw new PopulatorException(e.getMessage(), e);
    }
  }

  public EnumerationDto populateEnumeration(UUID uuid) throws PopulatorException {
    if (uuid == null)
      return null;

    try {
      Enumeration source = modelService.findOneByUuid(uuid, Enumeration.class);
      if (source == null) {
        return null;
      }

      EnumerationDto target = new EnumerationDto();
      target.setUuid(source.getUuid());
      target.setId(source.getId());
      target.setCreatedDate(source.getCreatedDate());
      target.setLastModifiedDate(source.getLastModifiedDate());
      target.setCreatedBy(populateAuditor(source.getCreatedBy()));
      target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

      target.setName(source.getName());
      target.setSort(source.getSort());

      return target;
    } catch (Exception e) {
      throw new PopulatorException(e.getMessage(), e);
    }
  }

}
