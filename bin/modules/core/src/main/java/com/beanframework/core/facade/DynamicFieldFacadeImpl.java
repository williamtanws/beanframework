package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.specification.DynamicFieldSpecification;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class DynamicFieldFacadeImpl extends AbstractFacade<DynamicField, DynamicFieldDto>
    implements DynamicFieldFacade {

  private static final Class<DynamicField> entityClass = DynamicField.class;
  private static final Class<DynamicFieldDto> dtoClass = DynamicFieldDto.class;

  @Override
  public DynamicFieldDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldDto findOneProperties(Map<String, Object> properties)
      throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldDto save(DynamicFieldDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<DynamicFieldDto> findPage(DataTableRequest dataTableRequest)
      throws BusinessException {
    return findPage(dataTableRequest,
        DynamicFieldSpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
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
  public DynamicFieldDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

}
