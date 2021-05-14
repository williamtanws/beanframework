package com.beanframework.core.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LogentryDto;
import com.beanframework.core.specification.LogentrySpecification;
import com.beanframework.logentry.domain.Logentry;

@Component
public class LogentryFacadeImpl extends AbstractFacade<Logentry, LogentryDto>
    implements LogentryFacade {

  private static final Class<Logentry> entityClass = Logentry.class;
  private static final Class<LogentryDto> dtoClass = LogentryDto.class;

  @Override
  public LogentryDto findOneByUuid(UUID uuid) throws Exception {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public LogentryDto findOneProperties(Map<String, Object> properties) throws Exception {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public LogentryDto create(LogentryDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public LogentryDto update(LogentryDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<LogentryDto> findPage(DataTableRequest dataTableRequest) throws Exception {
    return findPage(dataTableRequest, LogentrySpecification.getPageSpecification(dataTableRequest),
        entityClass, dtoClass);
  }

  @Override
  public int count() throws Exception {
    return count(entityClass);
  }

  @Override
  public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
    return findHistory(dataTableRequest, entityClass, dtoClass);
  }

  @Override
  public int countHistory(DataTableRequest dataTableRequest) throws Exception {
    return findCountHistory(dataTableRequest, entityClass);
  }

  @Override
  public LogentryDto createDto() throws Exception {
    return createDto(entityClass, dtoClass);
  }

  @Override
  public int removeAllLogentry() throws BusinessException {
    int count = 0;

    List<Logentry> oldLogentry = modelService.findAll(entityClass);
    count = oldLogentry.size();

    for (Logentry Logentry : oldLogentry) {
      modelService.deleteEntity(Logentry, entityClass);
    }

    return count;
  }

  @Override
  public int removeOldLogentryByToDate(Date date) throws Exception {
    int count = 0;

    List<Logentry> oldLogentry = modelService
        .findBySpecification(LogentrySpecification.getOldLogentryByToDate(date), entityClass);
    count = oldLogentry.size();

    for (Logentry Logentry : oldLogentry) {
      modelService.deleteEntity(Logentry, entityClass);
    }
    return count;
  }
}
