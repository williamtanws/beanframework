package com.beanframework.core.bean;

import java.util.List;

import com.beanframework.core.data.MenuDto;

public interface MenuNavigationBean {

	List<MenuDto> findMenuTreeByCurrentUser() throws Exception;

}
