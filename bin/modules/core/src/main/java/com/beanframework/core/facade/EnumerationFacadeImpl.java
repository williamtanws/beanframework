package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.specification.EnumerationSpecification;
import com.beanframework.enumuration.domain.Enumeration;

@Component
public class EnumerationFacadeImpl extends AbstractFacade<Enumeration, EnumerationDto>
    implements EnumerationFacade {

  private static final Class<Enumeration> entityClass = Enumeration.class;
  private static final Class<EnumerationDto> dtoClass = EnumerationDto.class;

  @Override
  public EnumerationDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public EnumerationDto findOneProperties(Map<String, Object> properties) throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public EnumerationDto save(EnumerationDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws BusinessException {
    return findPage(dataTableRequest,
        EnumerationSpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
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
  public EnumerationDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

}
