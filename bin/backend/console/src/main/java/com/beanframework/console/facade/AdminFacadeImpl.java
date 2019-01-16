package com.beanframework.console.facade;

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

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminService;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.data.AdminDto;
import com.beanframework.console.data.AdminSearch;
import com.beanframework.console.data.AdminSpecification;

@Component
public class AdminFacadeImpl implements AdminFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private AdminService adminService;

	@Override
	public Page<AdminDto> findPage(AdminSearch search, PageRequest pageable) throws Exception {
		Page<Admin> page = adminService.findEntityPage(search.toString(), AdminSpecification.findByCriteria(search), pageable);
		List<AdminDto> dtos = modelService.getDto(page.getContent(), AdminDto.class);
		return new PageImpl<AdminDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public AdminDto findOneByUuid(UUID uuid) throws Exception {
		Admin entity = adminService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, AdminDto.class);
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
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = adminService.findHistoryByUuid(uuid, firstResult, maxResults);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], AdminDto.class);
		}

		return revisions;
	}

	@Override
	public List<AdminDto> findAllDtoAdmins() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(AdminDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(adminService.findEntityBySorts(sorts), AdminDto.class);
	}

}
