package by.test.rest.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import by.test.rest.dao.EmployeesDao;
import by.test.rest.model.employee.Employee;
import by.test.rest.model.employee.EmployeeView;
import by.test.rest.service.employees.EmployeesService;
import by.test.rest.service.employees.EmployeesServiceImpl;

/**
 * 
 * @author Andrey Gorbachov
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class EmployeesServiceTest {

	@Mock
	EmployeesDao employeesDao;

	@InjectMocks
	EmployeesService employeesService = new EmployeesServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createEmployeeTest() {
		Employee employee = new Employee();
		employee.setName("CreateTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(1L);
		employeesService.create(employee);
		verify(employeesDao).create(employee);
	}

	@Test
	public void getEmployeeTest() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("GetTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(1L);
		when(employeesDao.read(1L)).thenReturn(employee);
		assertEquals(employee, employeesService.get(1L));
	}

	@Test
	public void updateEmployeeTest() {
		Employee employee = new Employee();
		employee.setName("UpdateTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(1L);
		employeesService.update(employee);
		verify(employeesDao).update(employee);
	}

	@Test
	public void deleteEmployeeTest() {
		Long employeeId = 1L;
		employeesService.delete(employeeId);
		verify(employeesDao).delete(employeeId);
	}

	@Test
	public void getAllViewEmployeeTest() {
		List<EmployeeView> employeesList = new ArrayList<>();
		EmployeeView employee = new EmployeeView();
		employee.setId(1L);
		employee.setName("One");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(1L);
		employee.setDepartmentName("DepartmentOne");
		employeesList.add(employee);
		employee.setId(2L);
		employee.setName("Two");
		employee.setDob(new Date());
		employee.setSalary(2000);
		employee.setDepartmentId(2L);
		employee.setDepartmentName("DepartmentTwo");
		employeesList.add(employee);
		when(employeesDao.findAllView()).thenReturn(employeesList);
		assertEquals(employeesList, employeesService.getAllView());
	}

	@Test
	public void searchAllByDepartmentIdAndDOBEmployeeTest() throws ParseException {
		List<EmployeeView> employeesByDepartmentId = new ArrayList<>();
		List<EmployeeView> employeesByDepartmentIdAndDate = new ArrayList<>();
		List<EmployeeView> employeesByDepartmentIdAndPeriodDate = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date startDate = dateFormat.parse("01.01.2001");
		Date dobDateOne = dateFormat.parse("02.01.2001");
		Date endDate = dateFormat.parse("03.01.2001");
		Date dobDateTwo = dateFormat.parse("04.01.2001");
		EmployeeView employee = new EmployeeView();
		employee.setId(1L);
		employee.setName("One");
		employee.setDob(dobDateOne);
		employee.setSalary(1000);
		employee.setDepartmentId(1L);
		employee.setDepartmentName("DepartmentTest");
		employeesByDepartmentId.add(employee);
		employeesByDepartmentIdAndDate.add(employee);
		employeesByDepartmentIdAndPeriodDate.add(employee);
		employee.setId(2L);
		employee.setName("Two");
		employee.setDob(dobDateTwo);
		employee.setSalary(2000);
		employee.setDepartmentId(1L);
		employee.setDepartmentName("DepartmentTest");
		employeesByDepartmentId.add(employee);

		when(employeesDao.findAllByDepartmentIdAndDOB(1L, null, null)).thenReturn(employeesByDepartmentId);
		when(employeesDao.findAllByDepartmentIdAndDOB(1L, dobDateOne, null)).thenReturn(employeesByDepartmentIdAndDate);
		when(employeesDao.findAllByDepartmentIdAndDOB(1L, startDate, endDate))
				.thenReturn(employeesByDepartmentIdAndPeriodDate);
		assertEquals(employeesByDepartmentId, employeesService.searchAllByDepartmentIdAndDOB(1L, null, null));
		assertEquals(employeesByDepartmentIdAndDate,
				employeesService.searchAllByDepartmentIdAndDOB(1L, dobDateOne, null));
		assertEquals(employeesByDepartmentIdAndPeriodDate,
				employeesService.searchAllByDepartmentIdAndDOB(1L, startDate, endDate));
	}

}
