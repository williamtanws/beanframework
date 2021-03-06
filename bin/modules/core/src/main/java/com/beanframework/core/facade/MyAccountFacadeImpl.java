package com.beanframework.core.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.CoreConstants;
import com.beanframework.core.data.MyAccountDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Component
public class MyAccountFacadeImpl extends AbstractFacade<User, MyAccountDto>
    implements MyAccountFacade {

  private static final Class<MyAccountDto> dtoClass = MyAccountDto.class;

  @Autowired
  private ModelService modelService;

  @Autowired
  private UserService userService;

  @Override
  public MyAccountDto getCurrentUser() throws BusinessException {
    User user = userService.getCurrentUserSession();
    return modelService.getDto(modelService.findOneByUuid(user.getUuid(), User.class), dtoClass);
  }

  @Override
  public MyAccountDto save(MyAccountDto dto) throws BusinessException {
    try {
      if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
        String mimetype = dto.getProfilePicture().getContentType();
        String type = mimetype.split("/")[0];
        if (type.equals("image") == Boolean.FALSE) {
          throw new Exception("Wrong picture format");
        }
      }
      User entity = modelService.getEntity(dto, CoreConstants.TypeCode.MYACCOUNT);
      entity = modelService.saveEntity(entity);

      userService.saveProfilePicture(entity, dto.getProfilePicture());

      userService.updateCurrentUserSession();

      return modelService.getDto(entity, dtoClass);

    } catch (Exception e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

}
