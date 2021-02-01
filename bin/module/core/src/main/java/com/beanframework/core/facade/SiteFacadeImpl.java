package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.cms.domain.Site;
import com.beanframework.cms.specification.SiteSpecification;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.SiteDto;

@Component
public class SiteFacadeImpl extends AbstractFacade<Site, SiteDto> implements SiteFacade {
	
	private static final Class<Site> entityClass = Site.class;
	private static final Class<SiteDto> dtoClass = SiteDto.class;

	@Override
	public SiteDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public SiteDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public SiteDto create(SiteDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public SiteDto update(SiteDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<SiteDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, SiteSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
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
	public SiteDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
