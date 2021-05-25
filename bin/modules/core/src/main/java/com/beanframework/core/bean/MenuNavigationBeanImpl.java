package com.beanframework.core.bean;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.facade.MenuFacade;
import com.beanframework.user.service.UserService;

public class MenuNavigationBeanImpl implements MenuNavigationBean {

  @Autowired
  private MenuFacade menuFacade;

  @Autowired
  private UserService userService;

  @Override
  public List<MenuDto> findMenuTreeByCurrentUser() throws Exception {
    return menuFacade.findMenuTreeByUser(userService.getCurrentUserSession());
  }

  @Override
  public List<MenuDto> findMenuBreadcrumbsByPath(String path) throws Exception {
    return menuFacade.findMenuBreadcrumbsByPath(path);
  }

}
