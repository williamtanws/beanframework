package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.core.specification.EmailSpecification;
import com.beanframework.email.domain.Email;

@Component
public class EmailFacadeImpl  extends AbstractFacade<Email, EmailDto> implements EmailFacade {
	
	private static final Class<Email> entityClass = Email.class;
	private static final Class<EmailDto> dtoClass = EmailDto.class;
	
	@Override
	public EmailDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public EmailDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public EmailDto create(EmailDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public EmailDto update(EmailDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<EmailDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, EmailSpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public EmailDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
