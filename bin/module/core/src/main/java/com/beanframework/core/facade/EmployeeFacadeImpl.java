package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.entity.EntityEmployeeProfileConverter;
import com.beanframework.core.converter.populator.EmployeeBasicPopulator;
import com.beanframework.core.converter.populator.EmployeeFullPopulator;
import com.beanframework.core.converter.populator.history.EmployeeHistoryPopulator;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.EmployeeSession;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.service.EmployeeService;
import com.beanframework.user.specification.EmployeeSpecification;

@Component
public class EmployeeFacadeImpl implements EmployeeFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EntityEmployeeProfileConverter entityEmployeeProfileConverter;

	@Autowired
	private EmployeeFullPopulator employeeFullPopulator;

	@Autowired
	private EmployeeBasicPopulator employeeBasicPopulator;

	@Autowired
	private EmployeeHistoryPopulator employeeHistoryPopulator;

	@Override
	public EmployeeDto findOneByUuid(UUID uuid) throws Exception {
		Employee entity = modelService.findOneByUuid(uuid, Employee.class);
		EmployeeDto dto = modelService.getDto(entity, EmployeeDto.class, new DtoConverterContext(employeeFullPopulator));

		return dto;
	}

	@Override
	public EmployeeDto findOneProperties(Map<String, Object> properties) throws Exception {
		Employee entity = modelService.findOneByProperties(properties, Employee.class);
		EmployeeDto dto = modelService.getDto(entity, EmployeeDto.class, new DtoConverterContext(employeeFullPopulator));

		return dto;
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

			Employee entity = modelService.getEntity(dto, Employee.class);
			entity = modelService.saveEntity(entity, Employee.class);

			employeeService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, EmployeeDto.class, new DtoConverterContext(employeeFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Employee.class);
		employeeService.deleteEmployeeProfilePictureByUuid(uuid);
	}

	@Override
	public Page<EmployeeDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Employee> page = modelService.findPage(EmployeeSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Employee.class);

		List<EmployeeDto> dtos = modelService.getDto(page.getContent(), EmployeeDto.class, new DtoConverterContext(employeeBasicPopulator));
		return new PageImpl<EmployeeDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Employee.class);
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

			entity = modelService.saveEntity(entity, Employee.class);
			employeeService.updatePrincipal(entity);
			employeeService.saveProfilePicture(entity, dto.getProfilePicture());

			return modelService.getDto(entity, EmployeeDto.class, new DtoConverterContext(employeeFullPopulator));

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public EmployeeDto getCurrentUser() throws Exception {
		Employee entity = employeeService.getCurrentUser();
		EmployeeDto dto = modelService.getDto(entity, EmployeeDto.class, new DtoConverterContext(employeeFullPopulator));

		return dto;
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = employeeService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Employee) {

				entityObject[0] = modelService.getDto(entityObject[0], EmployeeDto.class, new DtoConverterContext(employeeHistoryPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return employeeService.findCountHistory(dataTableRequest);
	}

	@Override
	public EmployeeDto createDto() throws Exception {
		Employee employee = modelService.create(Employee.class);
		return modelService.getDto(employee, EmployeeDto.class, new DtoConverterContext(employeeFullPopulator));
	}
}
