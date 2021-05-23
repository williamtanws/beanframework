package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.specification.DynamicFieldSlotSpecification;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@Component
public class DynamicFieldSlotFacadeImpl extends
    AbstractFacade<DynamicFieldSlot, DynamicFieldSlotDto> implements DynamicFieldSlotFacade {

  private static final Class<DynamicFieldSlot> entityClass = DynamicFieldSlot.class;
  private static final Class<DynamicFieldSlotDto> dtoClass = DynamicFieldSlotDto.class;

  @Override
  public DynamicFieldSlotDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldSlotDto findOneProperties(Map<String, Object> properties)
      throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldSlotDto save(DynamicFieldSlotDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<DynamicFieldSlotDto> findPage(DataTableRequest dataTableRequest)
      throws BusinessException {
    return findPage(dataTableRequest,
        DynamicFieldSlotSpecification.getPageSpecification(dataTableRequest), entityClass,
        dtoClass);
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
  public DynamicFieldSlotDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

}
