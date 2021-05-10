package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.specification.LanguageSpecification;
import com.beanframework.internationalization.domain.Language;

@Component
public class LanguageFacadeImpl extends AbstractFacade<Language, LanguageDto> implements LanguageFacade {
	
	private static final Class<Language> entityClass = Language.class;
	private static final Class<LanguageDto> dtoClass = LanguageDto.class;

	@Override
	public LanguageDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public LanguageDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public LanguageDto create(LanguageDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public LanguageDto update(LanguageDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<LanguageDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, LanguageSpecification.getPageSpecification(dataTableRequest), entityClass, dtoClass);
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
	public LanguageDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
