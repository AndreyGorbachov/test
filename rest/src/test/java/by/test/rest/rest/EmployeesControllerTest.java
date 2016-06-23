package by.test.rest.rest;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import by.test.rest.model.employee.Employee;
import by.test.rest.model.employee.EmployeeView;
import by.test.rest.rest.util.TestUtil;
import by.test.rest.service.employees.EmployeesService;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-context.xml")
@WebAppConfiguration
public class EmployeesControllerTest {

	MockMvc mockMvc;

	@Mock
	EmployeesService employeesService;

	@InjectMocks
	EmployeesController employeesController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(employeesController).build();
	}

	@Test
	public void getAllEmployeesTest() throws Exception {
		EmployeeView firstEmployee = new EmployeeView();
		firstEmployee.setId(1L);
		firstEmployee.setName("First");
		firstEmployee.setDob(new Date());
		firstEmployee.setSalary(1000);
		firstEmployee.setDepartmentId(1L);
		firstEmployee.setDepartmentName("First");
		EmployeeView secondEmployee = new EmployeeView();
		secondEmployee.setId(2L);
		secondEmployee.setName("Second");
		secondEmployee.setDob(new Date());
		secondEmployee.setSalary(2000);
		secondEmployee.setDepartmentId(2L);
		secondEmployee.setDepartmentName("Second");
		List<EmployeeView> employeesList = new ArrayList<>();
		employeesList.add(firstEmployee);
		employeesList.add(secondEmployee);

		when(employeesService.getAllView()).thenReturn(employeesList);

		mockMvc.perform(get("/employees")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(firstEmployee.getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(firstEmployee.getName())))
				.andExpect(jsonPath("$[0].dob", is(firstEmployee.getDob().getTime())))
				.andExpect(jsonPath("$[0].salary", is(firstEmployee.getSalary())))
				.andExpect(jsonPath("$[0].departmentId", is(firstEmployee.getDepartmentId().intValue())))
				.andExpect(jsonPath("$[0].departmentName", is(firstEmployee.getDepartmentName())))
				.andExpect(jsonPath("$[1].id", is(secondEmployee.getId().intValue())))
				.andExpect(jsonPath("$[1].name", is(secondEmployee.getName())))
				.andExpect(jsonPath("$[1].dob", is(secondEmployee.getDob().getTime())))
				.andExpect(jsonPath("$[1].salary", is(secondEmployee.getSalary())))
				.andExpect(jsonPath("$[1].departmentId", is(secondEmployee.getDepartmentId().intValue())))
				.andExpect(jsonPath("$[1].departmentName", is(secondEmployee.getDepartmentName())));

		verify(employeesService).getAllView();
		verifyNoMoreInteractions(employeesService);
	}

	@Test
	public void getEmployeeTest() throws Exception {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("GetTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(1L);

		when(employeesService.get(employee.getId())).thenReturn(employee);

		mockMvc.perform(get("/employees/{id}", employee.getId())).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id", is(employee.getId().intValue())))
				.andExpect(jsonPath("$.name", is(employee.getName())))
				.andExpect(jsonPath("$.dob", is(employee.getDob().getTime())))
				.andExpect(jsonPath("$.salary", is(employee.getSalary())))
				.andExpect(jsonPath("$.departmentId", is(employee.getDepartmentId().intValue())));

		verify(employeesService).get(employee.getId());
		verifyNoMoreInteractions(employeesService);
	}

	@Test
	public void createEmployeeTest() throws IOException, Exception {
		Employee employee = new Employee();
		employee.setName("CreateTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(1L);

		mockMvc.perform(post("/employees").contentType("application/json;charset=UTF-8")
				.content(TestUtil.convertObjectToJsonBytes(employee))).andExpect(status().isCreated());

		verify(employeesService).create(employee);
		verifyNoMoreInteractions(employeesService);
	}

	@Test
	public void updateEmployeeTest() throws IOException, Exception {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("UpdateTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(1L);

		when(employeesService.get(employee.getId())).thenReturn(employee);

		mockMvc.perform(put("/employees/{id}", employee.getId()).contentType("application/json;charset=UTF-8")
				.content(TestUtil.convertObjectToJsonBytes(employee))).andExpect(status().isOk());

		verify(employeesService).update(employee);
	}

	@Test
	public void deleteEmployeeTest() throws Exception {
		Long employeeId = 1L;

		mockMvc.perform(delete("/employees/{id}", employeeId)).andExpect(status().isNoContent());

		verify(employeesService).delete(employeeId);
		verifyNoMoreInteractions(employeesService);
	}

	@Test
	public void searchAllByDepartmentIdAndDOBEmployeeTest() throws Exception {
		Long departmentId = 1L;
		EmployeeView firstEmployee = new EmployeeView();
		firstEmployee.setId(1L);
		firstEmployee.setName("First");
		firstEmployee.setDob(new Date());
		firstEmployee.setSalary(1000);
		firstEmployee.setDepartmentId(departmentId);
		firstEmployee.setDepartmentName("First");
		EmployeeView secondEmployee = new EmployeeView();
		secondEmployee.setId(2L);
		secondEmployee.setName("Second");
		secondEmployee.setDob(new Date());
		secondEmployee.setSalary(2000);
		secondEmployee.setDepartmentId(departmentId);
		secondEmployee.setDepartmentName("First");
		List<EmployeeView> employeesList = new ArrayList<>();
		employeesList.add(firstEmployee);
		employeesList.add(secondEmployee);

		when(employeesService.searchAllByDepartmentIdAndDOB(departmentId, null, null)).thenReturn(employeesList);

		mockMvc.perform(get("/employees/{id}/search", departmentId)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(firstEmployee.getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(firstEmployee.getName())))
				.andExpect(jsonPath("$[0].dob", is(firstEmployee.getDob().getTime())))
				.andExpect(jsonPath("$[0].salary", is(firstEmployee.getSalary())))
				.andExpect(jsonPath("$[0].departmentId", is(firstEmployee.getDepartmentId().intValue())))
				.andExpect(jsonPath("$[0].departmentName", is(firstEmployee.getDepartmentName())))
				.andExpect(jsonPath("$[1].id", is(secondEmployee.getId().intValue())))
				.andExpect(jsonPath("$[1].name", is(secondEmployee.getName())))
				.andExpect(jsonPath("$[1].dob", is(secondEmployee.getDob().getTime())))
				.andExpect(jsonPath("$[1].salary", is(secondEmployee.getSalary())))
				.andExpect(jsonPath("$[1].departmentId", is(secondEmployee.getDepartmentId().intValue())))
				.andExpect(jsonPath("$[1].departmentName", is(secondEmployee.getDepartmentName())));

		verify(employeesService).searchAllByDepartmentIdAndDOB(departmentId, null, null);
		verifyNoMoreInteractions(employeesService);
	}

}
