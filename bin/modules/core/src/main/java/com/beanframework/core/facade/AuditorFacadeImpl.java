package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.Auditor;
import com.beanframework.core.specification.AuditorSpecification;

@Component
public class AuditorFacadeImpl extends AbstractFacade<Auditor, AuditorDto> implements AuditorFacade {
	
	private static final Class<Auditor> entityClass = Auditor.class;
	private static final Class<AuditorDto> dtoClass = AuditorDto.class;

	@Override
	public AuditorDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public AuditorDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public Page<AuditorDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, AuditorSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
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
}
