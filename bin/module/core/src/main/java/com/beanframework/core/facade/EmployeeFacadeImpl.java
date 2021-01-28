package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.converter.entity.EntityEmployeeProfileConverter;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.EmployeeSession;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.service.EmployeeService;
import com.beanframework.user.specification.EmployeeSpecification;

@Component
public class EmployeeFacadeImpl extends AbstractFacade<Employee, EmployeeDto> implements EmployeeFacade {
	
	private static final Class<Employee> entityClass = Employee.class;
	private static final Class<EmployeeDto> dtoClass = EmployeeDto.class;
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EntityEmployeeProfileConverter entityEmployeeProfileConverter;

	@Override
	public EmployeeDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
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
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == Boolean.FALSE) {
					throw new Exception("Wrong picture format");
				}
			}

			Employee entity = modelService.getEntity(dto, entityClass);
			entity = modelService.saveEntity(entity);

			employeeService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, dtoClass);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
		employeeService.deleteEmployeeProfilePictureByUuid(uuid);
	}

	@Override
	public Page<EmployeeDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, EmployeeSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public EmployeeDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
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
	public EmployeeDto saveProfile(EmployeeDto dto) throws BusinessException {

		try {
			if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
				String mimetype = dto.getProfilePicture().getContentType();
				String type = mimetype.split("/")[0];
				if (type.equals("image") == Boolean.FALSE) {
					throw new Exception("Wrong picture format");
				}
			}
			Employee entity = entityEmployeeProfileConverter.convert(dto);

			entity = modelService.saveEntity(entity);
			employeeService.updatePrincipal(entity);
			employeeService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, dtoClass);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public EmployeeDto getCurrentUser() throws Exception {
		Employee entity = employeeService.getCurrentUser();
		EmployeeDto dto = modelService.getDto(entity, dtoClass);

		return dto;
	}
}
