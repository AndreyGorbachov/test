package by.test.rest.rest;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
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

import by.test.rest.model.department.Department;
import by.test.rest.model.department.DepartmentView;
import by.test.rest.rest.util.TestUtil;
import by.test.rest.service.departments.DepartmentsService;

/**
 * 
 * @author Andrey Gorbachov
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-context.xml")
@WebAppConfiguration
public class DepartmentsControllerTest {

	MockMvc mockMvc;

	@Mock
	DepartmentsService departmentsService;

	@InjectMocks
	DepartmentsController departmentsController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(departmentsController).build();
	}

	@Test
	public void getAllDepartmentsTest() throws Exception {
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

		when(departmentsService.getAllView()).thenReturn(departmentsList);

		mockMvc.perform(get("/departments")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(firstDepartment.getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(firstDepartment.getName())))
				.andExpect(jsonPath("$[0].salary", is(firstDepartment.getSalary())))
				.andExpect(jsonPath("$[1].id", is(secondDepartment.getId().intValue())))
				.andExpect(jsonPath("$[1].name", is(secondDepartment.getName())))
				.andExpect(jsonPath("$[1].salary", is(secondDepartment.getSalary())));

		verify(departmentsService).getAllView();
		verifyNoMoreInteractions(departmentsService);
	}

	@Test
	public void getDepartmentTest() throws Exception {
		Department department = new Department();
		department.setId(1L);
		department.setName("GetTest");

		when(departmentsService.get(department.getId())).thenReturn(department);

		mockMvc.perform(get("/departments/{id}", department.getId())).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id", is(department.getId().intValue())))
				.andExpect(jsonPath("$.name", is(department.getName())));

		verify(departmentsService).get(department.getId());
		verifyNoMoreInteractions(departmentsService);

	}

	@Test
	public void createDepartmentTest() throws Exception {
		Department department = new Department();
		department.setName("CreateTest");

		mockMvc.perform(post("/departments").contentType("application/json;charset=UTF-8")
				.content(TestUtil.convertObjectToJsonBytes(department))).andExpect(status().isCreated());

		verify(departmentsService).create(department);
		verifyNoMoreInteractions(departmentsService);
	}

	@Test
	public void updateDepartmentTest() throws IOException, Exception {
		Department department = new Department();
		department.setId(1L);
		department.setName("UpdateTest");

		when(departmentsService.get(department.getId())).thenReturn(department);

		mockMvc.perform(put("/departments/{id}", department.getId()).contentType("application/json;charset=UTF-8")
				.content(TestUtil.convertObjectToJsonBytes(department))).andExpect(status().isOk());

		verify(departmentsService).update(department);
	}

	@Test
	public void deleteDepartmentTest() throws Exception {
		Long departmentId = 1L;

		mockMvc.perform(delete("/departments/{id}", departmentId))
				.andExpect(status().isNoContent());

		verify(departmentsService).delete(departmentId);
	}

}
