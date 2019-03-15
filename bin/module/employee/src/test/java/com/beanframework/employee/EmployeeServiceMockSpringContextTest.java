//package com.beanframework.employee;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.beanframework.employee.domain.Employee;
//import com.beanframework.employee.repository.EmployeeRepository;
//import com.beanframework.employee.service.EmployeeService;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class EmployeeServiceMockSpringContextTest {
//	
//	@MockBean
//	private EmployeeRepository employeeRepository;
//
//	@Autowired
//	private EmployeeService employeeService;
//	
//	@Test
//	public void testFindEmployeeById() {
//		Optional<Employee> employee = Optional.of(employeeService.create());
//		employee.get().setId("admin");
//		when(employeeRepository.findById("admin")).thenReturn(employee);
//		assertEquals("backoffice", employeeService.findById("admin").getId());
//	}
//}
