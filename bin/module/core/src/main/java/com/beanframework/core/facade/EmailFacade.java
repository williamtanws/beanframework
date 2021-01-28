package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmailDto;

public interface EmailFacade {

	EmailDto findOneByUuid(UUID uuid) throws Exception;

	EmailDto findOneProperties(Map<String, Object> properties) throws Exception;

	EmailDto create(EmailDto model) throws BusinessException;

	EmailDto update(EmailDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<EmailDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	void saveAttachment(EmailDto email, MultipartFile[] attachments) throws BusinessException;

	void deleteAttachment(UUID uuid, String filename) throws BusinessException;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	EmailDto createDto() throws Exception;
}
