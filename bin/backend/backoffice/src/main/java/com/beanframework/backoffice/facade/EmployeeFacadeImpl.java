package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.backoffice.data.EmployeeDto;
import com.beanframework.backoffice.data.EmployeeSearch;
import com.beanframework.backoffice.data.EmployeeSpecification;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.user.domain.UserField;

@Component
public class EmployeeFacadeImpl implements EmployeeFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EmployeeService employeeService;

	@Override
	public Page<EmployeeDto> findPage(EmployeeSearch search, PageRequest pageable) throws Exception {
		Page<Employee> page = employeeService.findEntityPage(search.toString(), EmployeeSpecification.findByCriteria(search), pageable);
		List<EmployeeDto> dtos = modelService.getDto(page.getContent(), EmployeeDto.class);
		return new PageImpl<EmployeeDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public EmployeeDto findOneByUuid(UUID uuid) throws Exception {
		Employee entity = employeeService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, EmployeeDto.class);
	}

	@Override
	public EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception {
		Employee entity = employeeService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, EmployeeDto.class);
	}

	@Override
	public EmployeeDto create(EmployeeDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public EmployeeDto update(EmployeeDto model) throws BusinessException {
		return save(model);
	}

	public EmployeeDto save(EmployeeDto dto) throws BusinessException {
		try {
			Employee entity = modelService.getEntity(dto, Employee.class);
			entity = (Employee) employeeService.saveEntity(entity);

			return modelService.getDto(entity, EmployeeDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		employeeService.deleteByUuid(uuid);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = employeeService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], EmployeeDto.class);
//		}

		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = employeeService.findHistoryByRelatedUuid(UserField.USER, uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserFieldDto.class);
//		}

		return revisions;
	}

	@Override
	public List<EmployeeDto> findAllDtoEmployees() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(EmployeeDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(employeeService.findEntityBySorts(sorts), EmployeeDto.class);
	}

	@Override
	public Set<EmployeeSession> findAllSessions() {
		return employeeService.findAllSessions();

	}

	@Override
	public void expireAllSessionsByUuid(UUID uuid) {
		employeeService.expireAllSessionsByUuid(uuid);
	}

	@Override
	public void expireAllSessions() {
		employeeService.expireAllSessions();
	}

	@Override
	public EmployeeDto saveProfile(EmployeeDto dto, MultipartFile picture) throws BusinessException {

		try {
			if (picture != null && picture.isEmpty() == false) {
				String mimetype = picture.getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == false) {
					throw new Exception("Wrong picture format");
				}
			}
			Employee entity = modelService.getEntity(dto, Employee.class);

			entity = (Employee) employeeService.saveEntity(entity);
			employeeService.updatePrincipal(entity);
			employeeService.saveProfilePicture(entity, picture);

			return modelService.getDto(entity, EmployeeDto.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public EmployeeDto getProfile() throws Exception {
		Employee employee = employeeService.getProfile();
		return modelService.getDto(employeeService.findOneEntityByUuid(employee.getUuid()), EmployeeDto.class);
	}
}
