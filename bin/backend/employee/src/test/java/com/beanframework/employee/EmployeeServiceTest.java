package com.beanframework.employee;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.employee.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	
	@Mock
	private ModelService modelService;

	@InjectMocks
	private EmployeeService employeeService = new EmployeeServiceImpl();
	
	@Test
	public void testFindEmployeeById() throws Exception {
		Employee employee = modelService.create(Employee.class);
		employee.setId("employee");
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Employee.ID, "employee");
		when(modelService.findOneEntityByProperties(properties, Employee.class)).thenReturn(employee);
	}
}
