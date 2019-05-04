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

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AuditorDto;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.specification.AuditorSpecification;

@Component
public class AuditorFacadeImpl implements AuditorFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;

	@Override
	public AuditorDto findOneByUuid(UUID uuid) throws Exception {
		Auditor entity = modelService.findByUuid(uuid, Auditor.class);

		return modelService.getDto(entity, AuditorDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public AuditorDto findOneProperties(Map<String, Object> properties) throws Exception {
		Auditor entity = modelService.findByProperties(properties, Auditor.class);

		return modelService.getDto(entity, AuditorDto.class);
	}

	@Override
	public Page<AuditorDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Auditor> page = modelService.findPage(AuditorSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Auditor.class);

		List<AuditorDto> dtos = modelService.getDto(page.getContent(), AuditorDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<AuditorDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Auditor.class);
	}

	@Override
	public List<AuditorDto> findAllDtoAuditors() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Auditor.CREATED_DATE, Sort.Direction.DESC);

		List<Auditor> auditor = modelService.findByPropertiesBySortByResult(null, sorts, null, null, Auditor.class);
		return modelService.getDto(auditor, AuditorDto.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = auditorService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Auditor) {

				entityObject[0] = modelService.getDto(entityObject[0], AuditorDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return auditorService.findCountHistory(dataTableRequest);
	}

}
