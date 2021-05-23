package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.specification.UserRightSpecification;
import com.beanframework.user.domain.UserRight;

@Component
public class UserRightFacadeImpl extends AbstractFacade<UserRight, UserRightDto>
    implements UserRightFacade {

  private static final Class<UserRight> entityClass = UserRight.class;
  private static final Class<UserRightDto> dtoClass = UserRightDto.class;

  @Override
  public UserRightDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public UserRightDto findOneProperties(Map<String, Object> properties) throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public UserRightDto save(UserRightDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<UserRightDto> findPage(DataTableRequest dataTableRequest) throws BusinessException {
    return findPage(dataTableRequest, UserRightSpecification.getPageSpecification(dataTableRequest),
        entityClass, dtoClass);
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
  public UserRightDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

  @Override
  public List<UserRightDto> findAllDtoUserRights() throws BusinessException {
    Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
    sorts.put(UserRight.SORT, Sort.Direction.ASC);

    List<UserRight> userRights =
        modelService.findByPropertiesBySortByResult(null, sorts, null, null, UserRight.class);
    return modelService.getDtoList(userRights, dtoClass);
  }
}
