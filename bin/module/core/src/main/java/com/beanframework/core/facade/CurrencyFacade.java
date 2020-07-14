package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CurrencyDto;

public interface CurrencyFacade {

	public static interface CurrencyPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "currency_read";
		public static final String AUTHORITY_CREATE = "currency_create";
		public static final String AUTHORITY_UPDATE = "currency_update";
		public static final String AUTHORITY_DELETE = "currency_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	CurrencyDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	CurrencyDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_CREATE)
	CurrencyDto create(CurrencyDto model) throws BusinessException;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_UPDATE)
	CurrencyDto update(CurrencyDto model) throws BusinessException;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	Page<CurrencyDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	List<CurrencyDto> findAllDtoCurrencys() throws Exception;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_CREATE)
	CurrencyDto createDto() throws Exception;

}
