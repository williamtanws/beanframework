package com.beanframework.employee;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.beanframework.common.service.ModelService;
import com.beanframework.employee.converter.DtoEmployeeConverter;
import com.beanframework.employee.converter.EntityEmployeeConverter;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.employee.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	
	@Mock
	private ModelService modelService;
	
	@Mock
	private EntityEmployeeConverter entityEmployeeConverter;

	@Mock
	private DtoEmployeeConverter dtoEmployeeConverter;

	@InjectMocks
	private EmployeeService employeeService = new EmployeeServiceImpl();
	
	@Test
	public void testFindEmployeeById() throws Exception {
		Employee employee = modelService.create(Employee.class);
		employee.setId("employee");
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Employee.ID, "employee");
		when(modelService.findOneDtoByProperties(properties, Employee.class)).thenReturn(employee);
		
		Employee convertedEmployee = modelService.create(Employee.class);
		convertedEmployee.setId("employee");
		when(dtoEmployeeConverter.convert(Mockito.any(Employee.class))).thenReturn(convertedEmployee);
		
		assertEquals("employee", modelService.findOneDtoByProperties(properties, Employee.class));
	}
}
