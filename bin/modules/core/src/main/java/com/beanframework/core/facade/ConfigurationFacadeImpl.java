package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.specification.ConfigurationSpecification;

@Component
public class ConfigurationFacadeImpl extends AbstractFacade<Configuration, ConfigurationDto>
    implements ConfigurationFacade {

  private static final Class<Configuration> entityClass = Configuration.class;
  private static final Class<ConfigurationDto> dtoClass = ConfigurationDto.class;

  @Autowired
  private ConfigurationService configurationService;

  @Override
  public ConfigurationDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public ConfigurationDto findOneProperties(Map<String, Object> properties)
      throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public ConfigurationDto save(ConfigurationDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<ConfigurationDto> findPage(DataTableRequest dataTableRequest)
      throws BusinessException {
    return findPage(dataTableRequest,
        ConfigurationSpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
  }

  @Override
  public int count() {
    return count(entityClass);
  }

  @Override
  public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException {
    return findHistory(dataTableRequest, entityClass, dtoClass);
  }

  @Override
  public int countHistory(DataTableRequest dataTableRequest) {
    return findCountHistory(dataTableRequest, entityClass);
  }

  @Override
  public ConfigurationDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

  @Override
  public String get(String id, String defaultValue) throws BusinessException {
    String value = configurationService.get(id);
    if (StringUtils.isBlank(value)) {
      return defaultValue;
    } else {
      return value;
    }
  }

  @Override
  public boolean is(String id, boolean defaultValue) throws BusinessException {
    String value = configurationService.get(id);
    if (StringUtils.isBlank(value)) {
      return defaultValue;
    } else {
      return BooleanUtils.parseBoolean(value);
    }
  }
}
