package com.beanframework.menu.service;

import java.util.UUID;

public interface MenuService {
	
	void savePosition(UUID fromUuid, UUID toUuid, int toIndex) throws Exception;
}
