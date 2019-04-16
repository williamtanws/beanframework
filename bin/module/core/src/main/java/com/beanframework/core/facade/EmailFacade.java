package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmailDto;

public interface EmailFacade {

	public static interface EmailPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "email_read";
		public static final String AUTHORITY_CREATE = "email_create";
		public static final String AUTHORITY_UPDATE = "email_update";
		public static final String AUTHORITY_DELETE = "email_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	EmailDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	EmailDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_CREATE)
	EmailDto create(EmailDto model) throws BusinessException;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_UPDATE)
	EmailDto update(EmailDto model) throws BusinessException;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	Page<EmailDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_UPDATE)
	void saveAttachment(EmailDto email, MultipartFile[] attachments) throws BusinessException;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_UPDATE)
	void deleteAttachment(UUID uuid, String filename) throws BusinessException;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_CREATE)
	EmailDto createDto() throws Exception;

}
