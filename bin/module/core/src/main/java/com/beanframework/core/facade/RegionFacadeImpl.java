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
import com.beanframework.core.converter.populator.RegionBasicPopulator;
import com.beanframework.core.converter.populator.RegionFullPopulator;
import com.beanframework.core.converter.populator.history.RegionHistoryPopulator;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.internationalization.service.RegionService;
import com.beanframework.internationalization.specification.RegionSpecification;

@Component
public class RegionFacadeImpl implements RegionFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private RegionFullPopulator regionFullPopulator;

	@Autowired
	private RegionBasicPopulator regionBasicPopulator;

	@Autowired
	private RegionHistoryPopulator regionHistoryPopulator;

	@Override
	public RegionDto findOneByUuid(UUID uuid) throws Exception {
		Region entity = modelService.findOneByUuid(uuid, Region.class);

		return modelService.getDto(entity, RegionDto.class, new DtoConverterContext(regionFullPopulator));
	}

	@Override
	public RegionDto findOneProperties(Map<String, Object> properties) throws Exception {
		Region entity = modelService.findOneByProperties(properties, Region.class);

		return modelService.getDto(entity, RegionDto.class, new DtoConverterContext(regionFullPopulator));
	}

	@Override
	public RegionDto create(RegionDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public RegionDto update(RegionDto model) throws BusinessException {
		return save(model);
	}

	public RegionDto save(RegionDto dto) throws BusinessException {
		try {
			Region entity = modelService.getEntity(dto, Region.class);
			entity = modelService.saveEntity(entity, Region.class);

			return modelService.getDto(entity, RegionDto.class, new DtoConverterContext(regionFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Region.class);
	}

	@Override
	public Page<RegionDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Region> page = modelService.findPage(RegionSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Region.class);

		List<RegionDto> dtos = modelService.getDto(page.getContent(), RegionDto.class, new DtoConverterContext(regionBasicPopulator));
		return new PageImpl<RegionDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Region.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = regionService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Region) {

				entityObject[0] = modelService.getDto(entityObject[0], RegionDto.class, new DtoConverterContext(regionHistoryPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return regionService.findCountHistory(dataTableRequest);
	}

	@Override
	public List<RegionDto> findAllDtoRegions() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Region.CREATED_DATE, Sort.Direction.DESC);

		List<Region> regions = modelService.findByPropertiesBySortByResult(null, sorts, null, null, Region.class);
		return modelService.getDto(regions, RegionDto.class, new DtoConverterContext(regionFullPopulator));
	}

	@Override
	public RegionDto createDto() throws Exception {
		Region region = modelService.create(Region.class);
		return modelService.getDto(region, RegionDto.class, new DtoConverterContext(regionFullPopulator));
	}

}
