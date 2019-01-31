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

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.specification.AuditorSpecification;
import com.beanframework.user.service.AuditorService;

@Component
public class AuditorFacadeImpl implements AuditorFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private AuditorService auditorService;

	@Override
	public AuditorDto findOneByUuid(UUID uuid) throws Exception {
		Auditor entity = auditorService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, AuditorDto.class);
	}

	@Override
	public AuditorDto findOneProperties(Map<String, Object> properties) throws Exception {
		Auditor entity = auditorService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, AuditorDto.class);
	}
	
	@Override
	public Page<AuditorDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Auditor> page = auditorService.findEntityPage(dataTableRequest, AuditorSpecification.getSpecification(dataTableRequest));
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<AuditorDto> dtos = modelService.getDto(page.getContent(), context, AuditorDto.class);
		return new PageImpl<AuditorDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return auditorService.count();
	}

	@Override
	public List<AuditorDto> findAllDtoAuditors() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Auditor.CREATED_DATE, Sort.Direction.DESC);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		return modelService.getDto(auditorService.findEntityBySorts(sorts, false), context, AuditorDto.class);
	}
	
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = auditorService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Auditor) {
				
				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(true);
				entityObject[0] = modelService.getDto(entityObject[0], context, AuditorDto.class);
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
