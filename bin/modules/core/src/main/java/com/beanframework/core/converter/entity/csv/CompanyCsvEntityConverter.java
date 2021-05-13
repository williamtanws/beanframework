package com.beanframework.core.converter.entity.csv;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.CompanyCsv;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;

@Component
public class CompanyCsvEntityConverter implements EntityCsvConverter<CompanyCsv, Company> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CompanyCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Company convert(CompanyCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Company.ID, source.getId());

        Company prototype = modelService.findOneByProperties(properties, Company.class);

        if (prototype != null) {

          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Company.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Company convertToEntity(CompanyCsv source, Company prototype) throws ConverterException {

    try {
      Date lastModifiedDate = new Date();

      if (StringUtils.isNotBlank(source.getId())) {
        prototype.setId(source.getId());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.isNotBlank(source.getName())) {
        prototype.setName(source.getName());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      // Address
      if (StringUtils.isNotBlank(source.getAddressIds())) {
        String[] AddressIds = source.getAddressIds().split(ImportListener.SPLITTER);
        for (int i = 0; i < AddressIds.length; i++) {
          boolean add = true;
          for (UUID Address : prototype.getAddresses()) {
            Address entity = modelService.findOneByUuid(Address, Address.class);
            if (StringUtils.equals(entity.getId(), AddressIds[i]))
              add = false;
          }

          if (add) {
            Map<String, Object> AddressProperties = new HashMap<String, Object>();
            AddressProperties.put(Address.ID, AddressIds[i]);
            Address Address = modelService.findOneByProperties(AddressProperties, Address.class);

            if (Address == null) {
              LOGGER.error("Address ID not exists: " + AddressIds[i]);
            } else {
              prototype.getAddresses().add(Address.getUuid());
              prototype.setLastModifiedDate(lastModifiedDate);
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}
