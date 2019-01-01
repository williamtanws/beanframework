package com.beanframework.admin.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.exception.BusinessException;

public interface AdminFacade {

	Admin getCurrentUser();

	Admin findDtoAuthenticate(String id, String password) throws Exception;

	Page<Admin> findDtoPage(Specification<Admin> findByCriteria, PageRequest of) throws Exception;

	Admin create() throws Exception;

	Admin findOneDtoByUuid(UUID uuid) throws Exception;

	Admin createDto(Admin adminCreate) throws BusinessException;

	Admin saveDto(Admin adminUpdate) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;
}
