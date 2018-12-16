package com.beanframework.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.user.domain.UserGroup;

public interface UserGroupService {

	void deleteLanguageByLanguageUuid(UUID uuid);

	Page<UserGroup> page(UserGroup userGroup, Pageable pageable);
}
