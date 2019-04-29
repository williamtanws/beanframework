package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminService;
import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AdminDto;

@Component
public class AdminFacadeImpl implements AdminFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AdminService adminService;

	@Override
	public AdminDto findOneByUuid(UUID uuid) throws Exception {

		Admin entity = adminService.findOneEntityByUuid(uuid);

		return modelService.getDto(entity, AdminDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public AdminDto findOneProperties(Map<String, Object> properties) throws Exception {
		Admin entity = adminService.findOneEntityByProperties(properties);

		return modelService.getDto(entity, AdminDto.class);
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

			return modelService.getDto(entity, AdminDto.class);
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
		Page<Admin> page = adminService.findEntityPage(dataTableRequest);

		List<AdminDto> dtos = modelService.getDto(page.getContent(), AdminDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<AdminDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return adminService.count();
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = adminService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Admin) {

				entityObject[0] = modelService.getDto(entityObject[0], AdminDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return adminService.findCountHistory(dataTableRequest);
	}

	@Override
	public AdminDto createDto() throws Exception {

		return modelService.getDto(adminService.create(), AdminDto.class);
	}

}
