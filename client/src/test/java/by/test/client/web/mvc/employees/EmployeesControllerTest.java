package by.test.client.web.mvc.employees;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import by.test.client.model.department.DepartmentView;
import by.test.client.model.employee.Employee;
import by.test.client.model.employee.EmployeeView;
import by.test.client.service.departments.DepartmentsService;
import by.test.client.service.employees.EmployeesService;
import by.test.client.web.mvc.ResponseForm;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-context.xml")
@WebAppConfiguration
public class EmployeesControllerTest {

	private MockMvc mockMvc;

	@Mock
	DepartmentsService departmentsService;

	@Mock
	EmployeesService employeesService;

	@Mock
	EmployeeValidator employeeValidator;

	@InjectMocks
	EmployeesController employeesController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(employeesController).build();

		when(employeeValidator.supports(Employee.class)).thenReturn(true);
	}

	@Test
	public void getEmployeesTest() throws Exception {
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

		ResponseForm form = new ResponseForm();
		form.setData(employeesList);
		when(employeesService.getAllView()).thenReturn(form);

		mockMvc.perform(get("/employees.html")).andExpect(status().isOk())
				.andExpect(
						view().name("employees"))
				.andExpect(forwardedUrl("employees"))
				.andExpect(
						model().attribute("employeesList",
								hasSize(2)))
				.andExpect(model().attribute("employeesList",
						hasItem(allOf(hasProperty("id", is(firstEmployee.getId())),
								hasProperty("name", is(firstEmployee.getName())),
								hasProperty("dob", is(firstEmployee.getDob())),
								hasProperty("salary", is(firstEmployee.getSalary())),
								hasProperty("departmentId", is(firstEmployee.getDepartmentId())),
								hasProperty("departmentName", is(firstEmployee.getDepartmentName()))))))
				.andExpect(model().attribute("employeesList",
						hasItem(allOf(hasProperty("id", is(secondEmployee.getId())),
								hasProperty("name", is(secondEmployee.getName())),
								hasProperty("dob", is(secondEmployee.getDob())),
								hasProperty("salary", is(secondEmployee.getSalary())),
								hasProperty("departmentId", is(secondEmployee.getDepartmentId())),
								hasProperty("departmentName", is(secondEmployee.getDepartmentName()))))));

		verify(employeesService).getAllView();
		verifyNoMoreInteractions(employeesService);
	}

	@Test
	public void addEmployeeTest() throws Exception {
		Employee employee = new Employee();

		DepartmentView firstDepartment = new DepartmentView();
		firstDepartment.setId(1L);
		firstDepartment.setName("First");
		firstDepartment.setSalary(1000);
		DepartmentView secondDepartment = new DepartmentView();
		secondDepartment.setId(2L);
		secondDepartment.setName("Second");
		secondDepartment.setSalary(2000);
		List<DepartmentView> departmentsList = new ArrayList<>();
		departmentsList.add(firstDepartment);
		departmentsList.add(secondDepartment);

		ResponseForm form = new ResponseForm();
		form.setData(departmentsList);
		when(departmentsService.getAllView()).thenReturn(form);

		mockMvc.perform(get("/add_employee.html")).andExpect(status().isOk()).andExpect(view().name("save_employee"))
				.andExpect(forwardedUrl("save_employee"))
				.andExpect(model().attribute("employeeForm", hasProperty("id", is(employee.getId()))))
				.andExpect(model().attribute("employeeForm", hasProperty("name", is(employee.getName()))))
				.andExpect(model().attribute("employeeForm", hasProperty("dob", is(employee.getDob()))))
				.andExpect(model().attribute("employeeForm", hasProperty("salary", is(employee.getSalary()))))
				.andExpect(
						model().attribute("employeeForm",
								hasProperty("departmentId",
										is(employee.getDepartmentId()))))
				.andExpect(
						model().attribute("departmentsList",
								hasSize(2)))
				.andExpect(model().attribute("departmentsList",
						hasItem(allOf(hasProperty("id", is(firstDepartment.getId())),
								hasProperty("name", is(firstDepartment.getName())),
								hasProperty("salary", is(firstDepartment.getSalary()))))))
				.andExpect(model().attribute("departmentsList",
						hasItem(allOf(hasProperty("id", is(secondDepartment.getId())),
								hasProperty("name", is(secondDepartment.getName())),
								hasProperty("salary", is(secondDepartment.getSalary()))))));

		verify(departmentsService).getAllView();
	}

	@Test
	public void editEmployeeTest() throws Exception {
		Long employeeId = 1L;
		Employee employee = new Employee();
		employee.setId(employeeId);
		employee.setName("EditTest");
		employee.setDob(new Date());
		employee.setDepartmentId(1L);

		ResponseForm getEmploeeform = new ResponseForm();
		getEmploeeform.setData(employee);
		when(employeesService.get(employeeId)).thenReturn(getEmploeeform);

		DepartmentView firstDepartment = new DepartmentView();
		firstDepartment.setId(1L);
		firstDepartment.setName("First");
		firstDepartment.setSalary(1000);
		DepartmentView secondDepartment = new DepartmentView();
		secondDepartment.setId(2L);
		secondDepartment.setName("Second");
		secondDepartment.setSalary(2000);
		List<DepartmentView> departmentsList = new ArrayList<>();
		departmentsList.add(firstDepartment);
		departmentsList.add(secondDepartment);

		ResponseForm getDepartmentsform = new ResponseForm();
		getDepartmentsform.setData(departmentsList);
		when(departmentsService.getAllView()).thenReturn(getDepartmentsform);

		mockMvc.perform(get("/edit_employee.html?id=" + employeeId)).andExpect(status().isOk())
				.andExpect(view().name("save_employee")).andExpect(forwardedUrl("save_employee"))
				.andExpect(model().attribute("employeeForm", hasProperty("id", is(employee.getId()))))
				.andExpect(model().attribute("employeeForm", hasProperty("name", is(employee.getName()))))
				.andExpect(model().attribute("employeeForm", hasProperty("dob", is(employee.getDob()))))
				.andExpect(model().attribute("employeeForm", hasProperty("salary", is(employee.getSalary()))))
				.andExpect(
						model().attribute("employeeForm",
								hasProperty("departmentId",
										is(employee.getDepartmentId()))))
				.andExpect(
						model().attribute("departmentsList",
								hasSize(2)))
				.andExpect(model().attribute("departmentsList",
						hasItem(allOf(hasProperty("id", is(firstDepartment.getId())),
								hasProperty("name", is(firstDepartment.getName())),
								hasProperty("salary", is(firstDepartment.getSalary()))))))
				.andExpect(model().attribute("departmentsList",
						hasItem(allOf(hasProperty("id", is(secondDepartment.getId())),
								hasProperty("name", is(secondDepartment.getName())),
								hasProperty("salary", is(secondDepartment.getSalary()))))));

		verify(employeesService).get(employeeId);
		verify(departmentsService).getAllView();
	}

	@Test
	public void saveEmployeeTest() throws Exception {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("SaveTest");
		employee.setDob(new Date());
		employee.setDepartmentId(1L);

		ResponseForm form = new ResponseForm();
		when(employeesService.save(employee)).thenReturn(form);

		mockMvc.perform(post("/save_employee.html").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("employeeForm", employee)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/employees.html")).andExpect(redirectedUrl("/employees.html"));

		verify(employeesService).save(employee);
	}

	@Test
	public void deleteEmployeeTest() throws Exception {
		Long employeeId = 1L;

		ResponseForm form = new ResponseForm();
		when(employeesService.delete(employeeId)).thenReturn(form);

		mockMvc.perform(get("/delete_employee.html?id=" + employeeId)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/employees.html")).andExpect(redirectedUrl("/employees.html"));

		verify(employeesService).delete(employeeId);
	}

}
