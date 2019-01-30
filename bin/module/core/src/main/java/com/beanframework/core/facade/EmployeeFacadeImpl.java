package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.EntityEmployeeProfileConverter;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.specification.EmployeeSpecification;
import com.beanframework.employee.EmployeeSession;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;

@Component
public class EmployeeFacadeImpl implements EmployeeFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EntityEmployeeProfileConverter entityEmployeeProfileConverter;

	@Override
	public EmployeeDto findOneByUuid(UUID uuid) throws Exception {
		Employee entity = employeeService.findOneEntityByUuid(uuid);
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(true);
		return modelService.getDto(entity, action, EmployeeDto.class);
	}

	@Override
	public EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception {
		Employee entity = employeeService.findOneEntityByProperties(properties);
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(true);
		return modelService.getDto(entity, action, EmployeeDto.class);
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

			ModelAction action = new ModelAction();
			action.setInitializeCollection(true);
			return modelService.getDto(entity, action, EmployeeDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		employeeService.deleteByUuid(uuid);
		employeeService.deleteEmployeeProfilePictureByUuid(uuid);
	}

	@Override
	public Page<EmployeeDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Employee> page = employeeService.findEntityPage(dataTableRequest, EmployeeSpecification.getSpecification(dataTableRequest));
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(false);
		List<EmployeeDto> dtos = modelService.getDto(page.getContent(), action, EmployeeDto.class);
		return new PageImpl<EmployeeDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return employeeService.count();
	}

	@Override
	public List<EmployeeDto> findAllDtoEmployees() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Employee.CREATED_DATE, Sort.Direction.DESC);
		
		ModelAction action = new ModelAction();
		action.setInitializeCollection(false);
		return modelService.getDto(employeeService.findEntityBySorts(sorts, false), action, EmployeeDto.class);
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
			Employee entity = entityEmployeeProfileConverter.convert(dto);

			entity = (Employee) employeeService.saveEntity(entity);
			employeeService.updatePrincipal(entity);
			employeeService.saveProfilePicture(entity, picture);

			ModelAction action = new ModelAction();
			action.setInitializeCollection(true);
			return modelService.getDto(entity, action, EmployeeDto.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public EmployeeDto getCurrentUser() throws Exception {
		Employee employee = employeeService.getCurrentUser();

		ModelAction action = new ModelAction();
		action.setInitializeCollection(true);
		return modelService.getDto(employeeService.findOneEntityByUuid(employee.getUuid()), action, EmployeeDto.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = employeeService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Employee) {

				ModelAction action = new ModelAction();
				action.setInitializeCollection(false);
				entityObject[0] = modelService.getDto(entityObject[0], action, EmployeeDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return employeeService.findCountHistory(dataTableRequest);
	}
}
