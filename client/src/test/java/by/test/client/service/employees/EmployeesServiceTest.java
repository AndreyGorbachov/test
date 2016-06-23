package by.test.client.service.employees;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import by.test.client.model.department.Department;
import by.test.client.model.employee.Employee;
import by.test.client.model.employee.EmployeeView;
import by.test.client.web.mvc.ResponseForm;
import by.test.client.web.mvc.departments.SearchForm;

/**
 * 
 * @author Andrey Gorbachov
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-context.xml")
public class EmployeesServiceTest {

	private MockRestServiceServer mockServer;

	@InjectMocks
	private EmployeesServiceImpl employeesService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockServer = MockRestServiceServer.createServer(employeesService.getRestTemplate());
	}

	@Test
	public void getEmployeeTest() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("EditTest");
		employee.setDob(new Date());
		employee.setDepartmentId(1L);

		ResponseForm expected = new ResponseForm();
		expected.setData(employee);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/employees/" + employee.getId()))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(mapper.writeValueAsString(employee), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = employeesService.get(employee.getId());
		mockServer.verify();
		assertEquals(expected.getData(), actual.getData());
	}

	@Test
	public void saveEmployeeCreateTest() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setName("EditTest");
		employee.setDob(new Date());
		employee.setDepartmentId(1L);

		ResponseForm expected = new ResponseForm();
		expected.setData(employee);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/employees")).andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess(mapper.writeValueAsString(employee), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = employeesService.save(employee);
		mockServer.verify();
		assertEquals(expected.getError(), actual.getError());
	}

	@Test
	public void saveEmployeeUpdateTest() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("EditTest");
		employee.setDob(new Date());
		employee.setDepartmentId(1L);

		ResponseForm expected = new ResponseForm();
		expected.setData(employee);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/employees/" + employee.getId()))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withSuccess(mapper.writeValueAsString(employee), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = employeesService.save(employee);
		mockServer.verify();
		assertEquals(expected.getError(), actual.getError());
	}

	@Test
	public void deleteEmployeeTest() throws JsonProcessingException {
		Long employeeId = 1L;
		ResponseForm expected = new ResponseForm();
		expected.setData(employeeId);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/employees/" + employeeId))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withSuccess(mapper.writeValueAsString(employeeId), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = employeesService.delete(employeeId);
		mockServer.verify();
		assertEquals(expected.getError(), actual.getError());
	}

	@Test
	public void getAllEmployeesTest() throws JsonProcessingException {
		EmployeeView firstEmployee = new EmployeeView();
		firstEmployee.setId(1L);
		firstEmployee.setName("First");
		firstEmployee.setDob(new Date());
		firstEmployee.setSalary(1000);
		firstEmployee.setDepartmentId(1L);
		firstEmployee.setDepartmentName("FirstDepartment");
		EmployeeView secondEmployee = new EmployeeView();
		secondEmployee.setId(2L);
		secondEmployee.setName("Second");
		secondEmployee.setDob(new Date());
		secondEmployee.setSalary(2000);
		secondEmployee.setDepartmentId(2L);
		secondEmployee.setDepartmentName("SecondDepartment");
		List<EmployeeView> employeesList = new ArrayList<>();
		employeesList.add(firstEmployee);
		employeesList.add(secondEmployee);

		ResponseForm expected = new ResponseForm();
		expected.setData(employeesList);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/employees")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(mapper.writeValueAsString(employeesList), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = employeesService.getAllView();
		mockServer.verify();
		assertEquals(expected.getData(), actual.getData());
	}

	@Test
	public void getAllByDepartmentIdAndDOBTest() throws JsonProcessingException {
		Department department = new Department();
		department.setId(1L);
		department.setName("SearchDepartment");
		EmployeeView firstEmployee = new EmployeeView();
		firstEmployee.setId(1L);
		firstEmployee.setName("First");
		firstEmployee.setDob(new Date());
		firstEmployee.setSalary(1000);
		firstEmployee.setDepartmentId(department.getId());
		firstEmployee.setDepartmentName(department.getName());
		EmployeeView secondEmployee = new EmployeeView();
		secondEmployee.setId(2L);
		secondEmployee.setName("Second");
		secondEmployee.setDob(new Date());
		secondEmployee.setSalary(2000);
		secondEmployee.setDepartmentId(department.getId());
		secondEmployee.setDepartmentName(department.getName());
		List<EmployeeView> employeesList = new ArrayList<>();
		employeesList.add(firstEmployee);
		employeesList.add(secondEmployee);

		ResponseForm expected = new ResponseForm();
		expected.setData(employeesList);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/employees/" + department.getId() + "/search"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(mapper.writeValueAsString(employeesList), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = employeesService.getAllByDepartmentIdAndDOB(department.getId(), new SearchForm());
		mockServer.verify();
		assertEquals(expected.getData(), actual.getData());
	}

}
