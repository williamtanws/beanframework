package com.beanframework.menu.service;

import java.util.List;
import java.util.UUID;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.menu.domain.Menu;

public interface MenuService {

  void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;

  void generateMenuAttribute(Menu model) throws Exception;

  List<Menu> findMenuBreadcrumbsByPath(String path) throws InterceptorException;
}
