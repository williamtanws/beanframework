package com.beanframework.email.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.email.domain.Email;

public interface EmailFacade {
	
	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('email_read')";
		public static final String CREATE = "hasAuthority('email_create')";
		public static final String UPDATE = "hasAuthority('email_update')";
		public static final String DELETE = "hasAuthority('email_delete')";
	}
	
	void saveAttachment(Email email, MultipartFile[] attachments) throws BusinessException;
	
	void deleteAttachment(UUID uuid, String filename) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<Email> findPage(Specification<Email> findByCriteria, PageRequest of) throws Exception;

	Email create() throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Email findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	void createDto(Email emailCreate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void updateDto(Email emailUpdate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;
 
}
