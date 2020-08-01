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
import com.beanframework.company.domain.Company;
import com.beanframework.company.service.CompanyService;
import com.beanframework.company.specification.CompanySpecification;
import com.beanframework.core.converter.populator.CompanyBasicPopulator;
import com.beanframework.core.converter.populator.CompanyFullPopulator;
import com.beanframework.core.data.CompanyDto;

@Component
public class CompanyFacadeImpl implements CompanyFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyFullPopulator companyFullPopulator;

	@Autowired
	private CompanyBasicPopulator companyBasicPopulator;

	@Override
	public CompanyDto findOneByUuid(UUID uuid) throws Exception {
		Company entity = modelService.findOneByUuid(uuid, Company.class);

		return modelService.getDto(entity, CompanyDto.class, new DtoConverterContext(companyFullPopulator));
	}

	@Override
	public CompanyDto findOneProperties(Map<String, Object> properties) throws Exception {
		Company entity = modelService.findOneByProperties(properties, Company.class);

		return modelService.getDto(entity, CompanyDto.class, new DtoConverterContext(companyFullPopulator));
	}

	@Override
	public CompanyDto create(CompanyDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public CompanyDto update(CompanyDto model) throws BusinessException {
		return save(model);
	}

	public CompanyDto save(CompanyDto dto) throws BusinessException {
		try {
			Company entity = modelService.getEntity(dto, Company.class);
			entity = modelService.saveEntity(entity, Company.class);

			return modelService.getDto(entity, CompanyDto.class, new DtoConverterContext(companyFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Company.class);
	}

	@Override
	public Page<CompanyDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Company> page = modelService.findPage(CompanySpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Company.class);

		List<CompanyDto> dtos = modelService.getDto(page.getContent(), CompanyDto.class, new DtoConverterContext(companyBasicPopulator));
		return new PageImpl<CompanyDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Company.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = companyService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Company) {

				entityObject[0] = modelService.getDto(entityObject[0], CompanyDto.class, new DtoConverterContext(companyFullPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return companyService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<CompanyDto> findAllDtoCompanys() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Company.CREATED_DATE, Sort.Direction.DESC);

		List<Company> companys = modelService.findByPropertiesBySortByResult(null, sorts, null, null, Company.class);
		return modelService.getDto(companys, CompanyDto.class, new DtoConverterContext(companyFullPopulator));
	}

	@Override
	public CompanyDto createDto() throws Exception {
		Company company = modelService.create(Company.class);
		return modelService.getDto(company, CompanyDto.class, new DtoConverterContext(companyFullPopulator));
	}

}
