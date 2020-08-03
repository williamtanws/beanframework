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
import com.beanframework.core.converter.populator.CountryBasicPopulator;
import com.beanframework.core.converter.populator.CountryFullPopulator;
import com.beanframework.core.converter.populator.history.CountryHistoryPopulator;
import com.beanframework.core.data.CountryDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.service.CountryService;
import com.beanframework.internationalization.specification.CountrySpecification;

@Component
public class CountryFacadeImpl implements CountryFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CountryFullPopulator countryFullPopulator;

	@Autowired
	private CountryBasicPopulator countryBasicPopulator;

	@Autowired
	private CountryHistoryPopulator countryHistoryPopulator;

	@Override
	public CountryDto findOneByUuid(UUID uuid) throws Exception {
		Country entity = modelService.findOneByUuid(uuid, Country.class);

		return modelService.getDto(entity, CountryDto.class, new DtoConverterContext(countryFullPopulator));
	}

	@Override
	public CountryDto findOneProperties(Map<String, Object> properties) throws Exception {
		Country entity = modelService.findOneByProperties(properties, Country.class);

		return modelService.getDto(entity, CountryDto.class, new DtoConverterContext(countryFullPopulator));
	}

	@Override
	public CountryDto create(CountryDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public CountryDto update(CountryDto model) throws BusinessException {
		return save(model);
	}

	public CountryDto save(CountryDto dto) throws BusinessException {
		try {
			Country entity = modelService.getEntity(dto, Country.class);
			entity = modelService.saveEntity(entity, Country.class);

			return modelService.getDto(entity, CountryDto.class, new DtoConverterContext(countryFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Country.class);
	}

	@Override
	public Page<CountryDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Country> page = modelService.findPage(CountrySpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Country.class);

		List<CountryDto> dtos = modelService.getDto(page.getContent(), CountryDto.class, new DtoConverterContext(countryBasicPopulator));
		return new PageImpl<CountryDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Country.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = countryService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Country) {

				entityObject[0] = modelService.getDto(entityObject[0], CountryDto.class, new DtoConverterContext(countryHistoryPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return countryService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<CountryDto> findAllDtoCountrys() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Country.CREATED_DATE, Sort.Direction.DESC);

		List<Country> countrys = modelService.findByPropertiesBySortByResult(null, sorts, null, null, Country.class);
		return modelService.getDto(countrys, CountryDto.class, new DtoConverterContext(countryBasicPopulator));
	}

	@Override
	public CountryDto createDto() throws Exception {
		Country country = modelService.create(Country.class);
		return modelService.getDto(country, CountryDto.class, new DtoConverterContext(countryBasicPopulator));
	}

}
