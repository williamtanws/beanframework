package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.specification.UserSpecification;
import com.beanframework.user.data.UserSession;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Service
public class UserFacadeImpl extends AbstractFacade<User, UserDto> implements UserFacade {

  private static final Class<User> entityClass = User.class;
  private static final Class<UserDto> dtoClass = UserDto.class;

  @Autowired
  private ModelService modelService;

  @Autowired
  private UserService userService;

  @Override
  public UserDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public UserDto findOneProperties(Map<String, Object> properties) throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public UserDto save(UserDto dto) throws BusinessException {
    try {
      if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
        String mimetype = dto.getProfilePicture().getContentType();
        String type = mimetype.split("/")[0];
        if (type.equals("image") == Boolean.FALSE) {
          throw new Exception("Wrong picture format");
        }
      }

      User entity = modelService.getEntity(dto, entityClass);
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
  public Page<UserDto> findPage(DataTableRequest dataTableRequest) throws BusinessException {
    return findPage(dataTableRequest, UserSpecification.getPageSpecification(dataTableRequest),
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
  public UserDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

  @Override
  public UserDto getCurrentUser() throws BusinessException {
    User user = userService.getCurrentUserSession();
    return modelService.getDto(user, dtoClass);
  }

  @Override
  public Set<UserSession> findAllSessions() {
    return userService.findAllSessions();

  }

  @Override
  public void expireAllSessionsByUuid(UUID uuid) {
    userService.expireAllSessionsByUuid(uuid);
  }

  @Override
  public void expireAllSessions() {
    userService.expireAllSessions();
  }

}
