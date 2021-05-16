package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.specification.CustomerSpecification;
import com.beanframework.user.domain.Customer;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Component
public class CustomerFacadeImpl extends AbstractFacade<Customer, CustomerDto>
    implements CustomerFacade {

  private static final Class<Customer> entityClass = Customer.class;
  private static final Class<CustomerDto> dtoClass = CustomerDto.class;

  @Autowired
  private UserService userService;

  @Override
  public CustomerDto findOneByUuid(UUID uuid) throws Exception {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public CustomerDto findOneProperties(Map<String, Object> properties) throws Exception {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public CustomerDto create(CustomerDto model) throws BusinessException {
    return save(model);
  }

  @Override
  public CustomerDto update(CustomerDto model) throws BusinessException {
    return save(model);
  }

  public CustomerDto save(CustomerDto dto) throws BusinessException {
    try {
      if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
        String mimetype = dto.getProfilePicture().getContentType();
        String type = mimetype.split("/")[0];
        if (type.equals("image") == Boolean.FALSE) {
          throw new Exception("Wrong picture format");
        }
      }

      Customer entity = modelService.getEntity(dto, entityClass);
      entity = modelService.saveEntity(entity);

      return modelService.getDto(entity, dtoClass);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<CustomerDto> findPage(DataTableRequest dataTableRequest) throws Exception {
    return findPage(dataTableRequest, CustomerSpecification.getPageSpecification(dataTableRequest),
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
  public CustomerDto createDto() throws Exception {
    return createDto(entityClass, dtoClass);
  }

  @Override
  public CustomerDto getCurrentUser() throws Exception {
    User user = userService.getCurrentUserSession();
    Customer customer = modelService.findOneByUuid(user.getUuid(), Customer.class);
    CustomerDto dto = modelService.getDto(customer, dtoClass);
    return dto;
  }
}
