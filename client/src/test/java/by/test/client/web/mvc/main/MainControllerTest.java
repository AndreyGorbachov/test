package by.test.client.web.mvc.main;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import by.test.client.model.department.DepartmentView;
import by.test.client.service.departments.DepartmentsService;
import by.test.client.web.mvc.ResponseForm;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-context.xml")
@WebAppConfiguration
public class MainControllerTest {

	private MockMvc mockMvc;

	@Mock
	DepartmentsService departmentsService;

	@InjectMocks
	MainController mainController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
	}

	@Test
	public void getMainTest() throws Exception {
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

		mockMvc.perform(get("/main.html")).andExpect(status().isOk())
				.andExpect(
						view().name("main"))
				.andExpect(forwardedUrl("main"))
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

}
