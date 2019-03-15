package com.beanframework.email.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.email.domain.Email;

public interface EmailService {

	Email create() throws Exception;

	Email findOneEntityByUuid(UUID uuid) throws Exception;

	Email findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Email> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;

	Email saveEntity(Email model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	void saveAttachment(Email email, MultipartFile[] attachments) throws IOException;

	void deleteAttachment(UUID uuid, String filename) throws IOException;

	<T> Page<Email> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
