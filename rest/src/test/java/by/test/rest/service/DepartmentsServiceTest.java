package by.test.rest.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import by.test.rest.dao.DepartmentsDao;
import by.test.rest.model.department.Department;
import by.test.rest.model.department.DepartmentView;
import by.test.rest.service.departments.DepartmentsService;
import by.test.rest.service.departments.DepartmentsServiceImpl;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DepartmentsServiceTest {

	@Mock
	DepartmentsDao departmentsDao;

	@InjectMocks
	DepartmentsService departmentsService = new DepartmentsServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createDepartmentTest() {
		Department department = new Department();
		department.setName("CreateTest");
		departmentsService.create(department);
		verify(departmentsDao).create(department);
	}

	@Test
	public void getDepartmentTest() {
		Long departmentId = 1L;
		Department department = new Department();
		department.setId(departmentId);
		department.setName("GetTest");
		when(departmentsDao.read(departmentId)).thenReturn(department);
		assertEquals(department, departmentsService.get(departmentId));
	}

	@Test
	public void updateDepartmentTest() {
		Department department = new Department();
		department.setId(1L);
		department.setName("UpdateTest");
		departmentsService.update(department);
		verify(departmentsDao).update(department);
	}

	@Test
	public void deleteDepartmentTest() {
		Long departmentId = 1L;
		departmentsService.delete(departmentId);
		verify(departmentsDao).delete(departmentId);
	}

	@Test
	public void findAllViewDepartmentsTest() {
		List<DepartmentView> departmentsList = new ArrayList<>();
		DepartmentView department = new DepartmentView();
		department.setId(1L);
		department.setName("depOne");
		department.setSalary(1000);
		departmentsList.add(department);
		department.setId(2L);
		department.setName("depTwo");
		department.setSalary(2000);
		departmentsList.add(department);
		when(departmentsDao.findAllView()).thenReturn(departmentsList);
		assertEquals(departmentsList, departmentsService.getAllView());
	}

}
