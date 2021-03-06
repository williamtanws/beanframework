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
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.specification.UserPermissionSpecification;
import com.beanframework.user.domain.UserPermission;

@Component
public class UserPermissionFacadeImpl extends AbstractFacade<UserPermission, UserPermissionDto>
    implements UserPermissionFacade {

  private static final Class<UserPermission> entityClass = UserPermission.class;
  private static final Class<UserPermissionDto> dtoClass = UserPermissionDto.class;

  @Override
  public UserPermissionDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public UserPermissionDto findOneProperties(Map<String, Object> properties)
      throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public UserPermissionDto save(UserPermissionDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<UserPermissionDto> findPage(DataTableRequest dataTableRequest)
      throws BusinessException {
    return findPage(dataTableRequest,
        UserPermissionSpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
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
  public UserPermissionDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

  @Override
  public List<UserPermissionDto> findAllDtoUserPermissions() throws BusinessException {
    Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
    sorts.put(UserPermission.SORT, Sort.Direction.ASC);

    List<UserPermission> userPermissions =
        modelService.findByPropertiesBySortByResult(null, sorts, null, null, UserPermission.class);
    return modelService.getDtoList(userPermissions, dtoClass);
  }
}
