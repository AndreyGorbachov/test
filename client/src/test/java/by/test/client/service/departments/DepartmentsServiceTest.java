package by.test.client.service.departments;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.ArrayList;
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
import by.test.client.model.department.DepartmentView;
import by.test.client.web.mvc.ResponseForm;

/**
 * 
 * @author Andrey Gorbachov
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-context.xml")
public class DepartmentsServiceTest {

	private MockRestServiceServer mockServer;

	@InjectMocks
	private DepartmentsServiceImpl departmentsService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockServer = MockRestServiceServer.createServer(departmentsService.getRestTemplate());
	}

	@Test
	public void getDepartmentTest() throws JsonProcessingException {
		Department department = new Department();
		department.setId(1L);
		department.setName("GetTest");
		ResponseForm expected = new ResponseForm();
		expected.setData(department);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/departments/" + department.getId()))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(mapper.writeValueAsString(department), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = departmentsService.get(department.getId());
		mockServer.verify();
		assertEquals(expected.getData(), actual.getData());
	}

	@Test
	public void saveDepartmentCreateTest() throws JsonProcessingException {
		Department department = new Department();
		department.setName("CreateTest");
		ResponseForm expected = new ResponseForm();
		expected.setData(department);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/departments")).andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess(mapper.writeValueAsString(department), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = departmentsService.save(department);
		mockServer.verify();
		assertEquals(expected.getError(), actual.getError());
	}

	@Test
	public void saveDepartmentUpdateTest() throws JsonProcessingException {
		Department department = new Department();
		department.setId(1L);
		department.setName("CreateTest");
		ResponseForm expected = new ResponseForm();
		expected.setData(department);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/departments/" + department.getId()))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withSuccess(mapper.writeValueAsString(department), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = departmentsService.save(department);
		mockServer.verify();
		assertEquals(expected.getError(), actual.getError());
	}

	@Test
	public void deleteDepartmentTest() throws JsonProcessingException {
		Long departmentId = 1L;
		ResponseForm expected = new ResponseForm();
		expected.setData(departmentId);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/departments/" + departmentId))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withSuccess(mapper.writeValueAsString(departmentId), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = departmentsService.delete(departmentId);
		mockServer.verify();
		assertEquals(expected.getError(), actual.getError());
	}

	@Test
	public void getAllViewDepartmentTest() throws JsonProcessingException {
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
		ResponseForm expected = new ResponseForm();
		expected.setData(departmentsList);

		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(requestTo("http://localhost:8080/rest/departments")).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(mapper.writeValueAsString(departmentsList), MediaType.APPLICATION_JSON));

		ResponseForm actual = new ResponseForm();
		actual = departmentsService.getAllView();
		mockServer.verify();
		assertEquals(expected.getData(), actual.getData());
	}

}
