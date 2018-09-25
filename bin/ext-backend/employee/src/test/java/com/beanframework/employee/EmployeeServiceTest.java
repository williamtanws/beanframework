package com.beanframework.employee;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.beanframework.employee.converter.DtoEmployeeConverter;
import com.beanframework.employee.converter.EntityEmployeeConverter;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.repository.EmployeeRepository;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.employee.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Mock
	private EntityEmployeeConverter entityEmployeeConverter;

	@Mock
	private DtoEmployeeConverter dtoEmployeeConverter;

	@InjectMocks
	private EmployeeService employeeService = new EmployeeServiceImpl();
	
	@Test
	public void testFindEmployeeById() {
		Optional<Employee> employee = Optional.of(employeeService.create());
		employee.get().setId("employee");
		when(employeeRepository.findById("employee")).thenReturn(employee);
		
		Employee convertedEmployee = employeeService.create();
		convertedEmployee.setId("employee");
		when(dtoEmployeeConverter.convert(Mockito.any(Employee.class))).thenReturn(convertedEmployee);
		
		assertEquals("employee", employeeService.findById("employee").getId());
	}
}
