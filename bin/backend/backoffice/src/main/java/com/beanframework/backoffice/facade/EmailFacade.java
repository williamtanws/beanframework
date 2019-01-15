package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.backoffice.data.EmailDto;
import com.beanframework.common.exception.BusinessException;

public interface EmailFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('email_read')";
		public static final String CREATE = "hasAuthority('email_create')";
		public static final String UPDATE = "hasAuthority('email_update')";
		public static final String DELETE = "hasAuthority('email_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<EmailDto> findPage(Specification<EmailDto> findByCriteria, PageRequest of) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	EmailDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	EmailDto create(EmailDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	EmailDto update(EmailDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void saveAttachment(EmailDto email, MultipartFile[] attachments) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void deleteAttachment(UUID uuid, String filename) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	EmailDto findOneByProperties(Map<String, Object> properties) throws Exception;
}
