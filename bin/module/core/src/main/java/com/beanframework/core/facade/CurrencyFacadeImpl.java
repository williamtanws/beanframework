package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.populator.CurrencyBasicPopulator;
import com.beanframework.core.converter.populator.CurrencyFullPopulator;
import com.beanframework.core.converter.populator.history.CurrencyHistoryPopulator;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.internationalization.domain.Currency;
import com.beanframework.internationalization.service.CurrencyService;
import com.beanframework.internationalization.specification.CurrencySpecification;

@Component
public class CurrencyFacadeImpl implements CurrencyFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private CurrencyFullPopulator currencyFullPopulator;

	@Autowired
	private CurrencyBasicPopulator currencyBasicPopulator;

	@Autowired
	private CurrencyHistoryPopulator currencyHistoryPopulator;

	@Override
	public CurrencyDto findOneByUuid(UUID uuid) throws Exception {
		Currency entity = modelService.findOneByUuid(uuid, Currency.class);

		return modelService.getDto(entity, CurrencyDto.class, new DtoConverterContext(currencyFullPopulator));
	}

	@Override
	public CurrencyDto findOneProperties(Map<String, Object> properties) throws Exception {
		Currency entity = modelService.findOneByProperties(properties, Currency.class);

		return modelService.getDto(entity, CurrencyDto.class, new DtoConverterContext(currencyFullPopulator));
	}

	@Override
	public CurrencyDto create(CurrencyDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public CurrencyDto update(CurrencyDto model) throws BusinessException {
		return save(model);
	}

	public CurrencyDto save(CurrencyDto dto) throws BusinessException {
		try {
			Currency entity = modelService.getEntity(dto, Currency.class);
			entity = modelService.saveEntity(entity, Currency.class);

			return modelService.getDto(entity, CurrencyDto.class, new DtoConverterContext(currencyFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Currency.class);
	}

	@Override
	public Page<CurrencyDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Currency> page = modelService.findPage(CurrencySpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Currency.class);

		List<CurrencyDto> dtos = modelService.getDto(page.getContent(), CurrencyDto.class, new DtoConverterContext(currencyBasicPopulator));
		return new PageImpl<CurrencyDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Currency.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = currencyService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Currency) {

				entityObject[0] = modelService.getDto(entityObject[0], CurrencyDto.class, new DtoConverterContext(currencyHistoryPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return currencyService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<CurrencyDto> findAllDtoCurrencys() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Currency.CREATED_DATE, Sort.Direction.DESC);

		List<Currency> currencys = modelService.findByPropertiesBySortByResult(null, sorts, null, null, Currency.class);
		return modelService.getDto(currencys, CurrencyDto.class, new DtoConverterContext(currencyFullPopulator));
	}

	@Override
	public CurrencyDto createDto() throws Exception {
		Currency currency = modelService.create(Currency.class);
		return modelService.getDto(currency, CurrencyDto.class, new DtoConverterContext(currencyFullPopulator));
	}

}
