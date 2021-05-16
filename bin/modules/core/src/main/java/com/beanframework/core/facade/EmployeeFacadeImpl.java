package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.specification.EmployeeSpecification;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Component
public class EmployeeFacadeImpl extends AbstractFacade<Employee, EmployeeDto>
    implements EmployeeFacade {

  private static final Class<Employee> entityClass = Employee.class;
  private static final Class<EmployeeDto> dtoClass = EmployeeDto.class;

  @Autowired
  private UserService userService;

  @Override
  public EmployeeDto findOneByUuid(UUID uuid) throws Exception {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public EmployeeDto create(EmployeeDto model) throws BusinessException {
    return save(model);
  }

  @Override
  public EmployeeDto update(EmployeeDto model) throws BusinessException {
    return save(model);
  }

  public EmployeeDto save(EmployeeDto dto) throws BusinessException {
    try {
      if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
        String mimetype = dto.getProfilePicture().getContentType();
        String type = mimetype.split("/")[0];
        if (type.equals("image") == Boolean.FALSE) {
          throw new Exception("Wrong picture format");
        }
      }

      Employee entity = modelService.getEntity(dto, entityClass);
      entity = modelService.saveEntity(entity);

      userService.saveProfilePicture(entity, dto.getProfilePicture());

      return modelService.getDto(entity, dtoClass);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
    userService.deleteProfilePictureFileByUuid(uuid);
  }

  @Override
  public Page<EmployeeDto> findPage(DataTableRequest dataTableRequest) throws Exception {
    return findPage(dataTableRequest, EmployeeSpecification.getPageSpecification(dataTableRequest),
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
  public EmployeeDto createDto() throws Exception {
    return createDto(entityClass, dtoClass);
  }

  @Override
  public EmployeeDto getCurrentUser() throws Exception {
    User user = userService.getCurrentUserSession();
    Employee employee = modelService.findOneByUuid(user.getUuid(), Employee.class);
    EmployeeDto dto = modelService.getDto(employee, dtoClass);
    return dto;
  }
}
