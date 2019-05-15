package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.workflow.domain.Workflow;
import com.beanframework.workflow.service.WorkflowService;
import com.beanframework.workflow.specification.WorkflowSpecification;

@Component
public class WorkflowFacadeImpl implements WorkflowFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private WorkflowService workflowService;

	@Override
	public WorkflowDto findOneByUuid(UUID uuid) throws Exception {
		Workflow entity = modelService.findOneByUuid(uuid, Workflow.class);
		return modelService.getDto(entity, WorkflowDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public WorkflowDto findOneProperties(Map<String, Object> properties) throws Exception {
		Workflow entity = modelService.findOneByProperties(properties, Workflow.class);
		return modelService.getDto(entity, WorkflowDto.class);
	}

	@Override
	public WorkflowDto create(WorkflowDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public WorkflowDto update(WorkflowDto model) throws BusinessException {
		return save(model);
	}

	public WorkflowDto save(WorkflowDto dto) throws BusinessException {
		try {
			Workflow entity = modelService.getEntity(dto, Workflow.class);
			entity = modelService.saveEntity(entity, Workflow.class);
			
			return modelService.getDto(entity, WorkflowDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Workflow.class);
	}

	@Override
	public Page<WorkflowDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Workflow> page = modelService.findPage(WorkflowSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Workflow.class);

		List<WorkflowDto> dtos = modelService.getDto(page.getContent(), WorkflowDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<WorkflowDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Workflow.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = workflowService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Workflow) {

				entityObject[0] = modelService.getDto(entityObject[0], WorkflowDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return workflowService.findCountHistory(dataTableRequest);
	}

	@Override
	public WorkflowDto createDto() throws Exception {
		Workflow Workflow = modelService.create(Workflow.class);
		return modelService.getDto(Workflow, WorkflowDto.class);
	}
}
