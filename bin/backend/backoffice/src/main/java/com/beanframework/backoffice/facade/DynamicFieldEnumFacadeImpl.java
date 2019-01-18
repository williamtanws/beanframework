package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.backoffice.data.DynamicFieldEnumSearch;
import com.beanframework.backoffice.data.DynamicFieldEnumSpecification;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;
import com.beanframework.dynamicfield.service.DynamicFieldEnumService;

@Component
public class DynamicFieldEnumFacadeImpl implements DynamicFieldEnumFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DynamicFieldEnumService dynamicFieldEnumService;

	@Override
	public Page<DynamicFieldEnumDto> findPage(DynamicFieldEnumSearch search, PageRequest pageable) throws Exception {
		Page<DynamicFieldEnum> page = dynamicFieldEnumService.findEntityPage(search.toString(), DynamicFieldEnumSpecification.findByCriteria(search), pageable);
		List<DynamicFieldEnumDto> dtos = modelService.getDto(page.getContent(), DynamicFieldEnumDto.class);
		return new PageImpl<DynamicFieldEnumDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public DynamicFieldEnumDto findOneByUuid(UUID uuid) throws Exception {
		DynamicFieldEnum entity = dynamicFieldEnumService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, DynamicFieldEnumDto.class);
	}

	@Override
	public DynamicFieldEnumDto findOneProperties(Map<String, Object> properties) throws Exception {
		DynamicFieldEnum entity = dynamicFieldEnumService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, DynamicFieldEnumDto.class);
	}

	@Override
	public DynamicFieldEnumDto create(DynamicFieldEnumDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public DynamicFieldEnumDto update(DynamicFieldEnumDto model) throws BusinessException {
		return save(model);
	}

	public DynamicFieldEnumDto save(DynamicFieldEnumDto dto) throws BusinessException {
		try {
			DynamicFieldEnum entity = modelService.getEntity(dto, DynamicFieldEnum.class);
			entity = (DynamicFieldEnum) dynamicFieldEnumService.saveEntity(entity);

			return modelService.getDto(entity, DynamicFieldEnumDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		dynamicFieldEnumService.deleteByUuid(uuid);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = dynamicFieldEnumService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], DynamicFieldEnumDto.class);
//		}

		return revisions;
	}

	@Override
	public List<DynamicFieldEnumDto> findAllDtoDynamicFieldEnums() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(DynamicFieldEnumDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(dynamicFieldEnumService.findEntityBySorts(sorts), DynamicFieldEnumDto.class);
	}

}
