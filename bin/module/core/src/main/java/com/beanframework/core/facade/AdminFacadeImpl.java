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

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminService;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AdminDto;
import com.beanframework.core.specification.AdminSpecification;

@Component
public class AdminFacadeImpl implements AdminFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private AdminService adminService;

	@Override
	public AdminDto findOneByUuid(UUID uuid) throws Exception {
		Admin entity = adminService.findOneEntityByUuid(uuid);
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(true);
		return modelService.getDto(entity, action, AdminDto.class);
	}

	@Override
	public AdminDto findOneProperties(Map<String, Object> properties) throws Exception {
		Admin entity = adminService.findOneEntityByProperties(properties);
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(true);
		return modelService.getDto(entity, action, AdminDto.class);
	}

	@Override
	public AdminDto create(AdminDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public AdminDto update(AdminDto model) throws BusinessException {
		return save(model);
	}

	public AdminDto save(AdminDto dto) throws BusinessException {
		try {
			Admin entity = modelService.getEntity(dto, Admin.class);
			entity = (Admin) adminService.saveEntity(entity);

			ModelAction action = new ModelAction();
			action.setInitializeCollection(true);
			return modelService.getDto(entity, action, AdminDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		adminService.deleteByUuid(uuid);
	}
	
	@Override
	public Page<AdminDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Admin> page = adminService.findEntityPage(dataTableRequest, AdminSpecification.getSpecification(dataTableRequest));
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(false);
		List<AdminDto> dtos = modelService.getDto(page.getContent(), action, AdminDto.class);
		return new PageImpl<AdminDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return adminService.count();
	}

	@Override
	public List<AdminDto> findAllDtoAdmins() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Admin.CREATED_DATE, Sort.Direction.DESC);
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(false);
		return modelService.getDto(adminService.findEntityBySorts(sorts, false), action, AdminDto.class);
	}
	
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = adminService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Admin) {
				
				ModelAction action = new ModelAction();
				action.setInitializeCollection(false);
				entityObject[0] = modelService.getDto(entityObject[0], action, AdminDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return adminService.findCountHistory(dataTableRequest);
	}

}
