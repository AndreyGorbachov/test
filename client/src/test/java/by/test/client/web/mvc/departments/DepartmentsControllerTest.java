package by.test.client.web.mvc.departments;

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

import by.test.client.model.department.Department;
import by.test.client.model.department.DepartmentView;
import by.test.client.service.departments.DepartmentsService;
import by.test.client.service.employees.EmployeesService;
import by.test.client.web.mvc.ResponseForm;
import by.test.client.model.employee.EmployeeView;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-context.xml")
@WebAppConfiguration
public class DepartmentsControllerTest {

	private MockMvc mockMvc;

	@Mock
	DepartmentsService departmentsService;

	@Mock
	EmployeesService employeesService;

	@Mock
	DapartmentValidator departmentValidator;

	@InjectMocks
	DepartmentsController departmentsController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(departmentsController).build();

		when(departmentValidator.supports(Department.class)).thenReturn(true);
	}

	@Test
	public void getDepartmentsTest() throws Exception {
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

		mockMvc.perform(get("/departments.html")).andExpect(status().isOk())
				.andExpect(
						view().name("departments"))
				.andExpect(forwardedUrl("departments"))
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
		verifyNoMoreInteractions(departmentsService);
	}

	@Test
	public void viewDepartmentTest() throws Exception {
		Long departmentId = 1L;

		Department department = new Department();
		department.setId(departmentId);
		department.setName("GetTest");

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

		ResponseForm depForm = new ResponseForm();
		depForm.setData(department);
		when(departmentsService.get(departmentId)).thenReturn(depForm);

		ResponseForm empForm = new ResponseForm();
		empForm.setData(employeesList);
		SearchForm searchForm = new SearchForm();
		when(employeesService.getAllByDepartmentIdAndDOB(departmentId, searchForm)).thenReturn(empForm);

		mockMvc.perform(get("/view_department.html?departmentId=" + departmentId).flashAttr("searchForm", searchForm))
				.andExpect(status().isOk()).andExpect(view().name("view_department"))
				.andExpect(forwardedUrl("view_department"))
				.andExpect(model().attribute("department", hasProperty("id", is(department.getId()))))
				.andExpect(model().attribute("department", hasProperty("name", is(department.getName()))))
				.andExpect(
						model().attribute("department",
								hasProperty("name",
										is(department.getName()))))
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

		verify(departmentsService).get(departmentId);
		verify(employeesService).getAllByDepartmentIdAndDOB(departmentId, searchForm);

	}

	@Test
	public void addDepartmentTest() throws Exception {
		Department department = new Department();

		mockMvc.perform(get("/add_department.html")).andExpect(status().isOk())
				.andExpect(view().name("save_department")).andExpect(forwardedUrl("save_department"))
				.andExpect(model().attribute("departmentForm", hasProperty("id", is(department.getId()))))
				.andExpect(model().attribute("departmentForm", hasProperty("name", is(department.getName()))));
	}

	@Test
	public void editDepartmentTest() throws Exception {
		Department department = new Department();
		department.setId(1L);
		department.setName("EditTest");

		ResponseForm form = new ResponseForm();
		form.setData(department);
		when(departmentsService.get(department.getId())).thenReturn(form);

		mockMvc.perform(get("/edit_department.html?id=" + department.getId())).andExpect(status().isOk())
				.andExpect(view().name("save_department")).andExpect(forwardedUrl("save_department"))
				.andExpect(model().attribute("departmentForm", hasProperty("id", is(department.getId()))))
				.andExpect(model().attribute("departmentForm", hasProperty("name", is(department.getName()))));
	}

	@Test
	public void saveDepartmentTest() throws Exception {
		Department department = new Department();
		department.setId(1L);
		department.setName("SaveTest");

		ResponseForm form = new ResponseForm();
		when(departmentsService.save(department)).thenReturn(form);

		mockMvc.perform(post("/save_department.html").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("departmentForm", department)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/departments.html")).andExpect(redirectedUrl("/departments.html"));

		verify(departmentsService).save(department);
	}

	@Test
	public void deleteDepartmentTest() throws Exception {
		Long departmentId = 1L;

		ResponseForm form = new ResponseForm();
		when(departmentsService.delete(departmentId)).thenReturn(form);

		mockMvc.perform(get("/delete_department.html?id=" + departmentId)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/departments.html")).andExpect(redirectedUrl("/departments.html"));

		verify(departmentsService).delete(departmentId);
	}

}
