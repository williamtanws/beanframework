package com.beanframework.core.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.AddressCsv;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.user.domain.Address;

@Component
public class AddressCsvEntityConverter implements EntityCsvConverter<AddressCsv, Address> {

  protected static Logger LOGGER = LoggerFactory.getLogger(AddressCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Address convert(AddressCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Address.ID, source.getId());

        Address prototype = modelService.findOneByProperties(properties, Address.class);

        if (prototype != null) {

          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Address.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Address convertToEntity(AddressCsv source, Address prototype) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        prototype.setId(source.getId());
      }

      if (StringUtils.isNotBlank(source.getStreetName())) {
        prototype.setStreetName(source.getStreetName());
      }

      if (StringUtils.isNotBlank(source.getStreetNumber())) {
        prototype.setStreetNumber(source.getStreetNumber());
      }

      if (StringUtils.isNotBlank(source.getPostalCode())) {
        prototype.setPostalCode(source.getPostalCode());
      }

      if (StringUtils.isNotBlank(source.getTown())) {
        prototype.setTown(source.getTown());
      }

      // Country
      if (StringUtils.isNotBlank(source.getCountryId())) {
        Map<String, Object> parentProperties = new HashMap<String, Object>();
        parentProperties.put(Country.ID, source.getCountryId());
        Country entity = modelService.findOneByProperties(parentProperties, Country.class);

        if (entity == null) {
          LOGGER.error("Country ID not exists: " + source.getCountryId());
        } else {
          prototype.setCountry(entity.getUuid());
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}
