package by.test.rest.dao;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.test.rest.model.department.Department;
import by.test.rest.model.employee.Employee;
import by.test.rest.model.employee.EmployeeView;

/**
 * @author Andrey Gorbachov
 *
 */
@ContextConfiguration(locations = "classpath:config/application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeesDaoTest {

	@Autowired
	private EmployeesDao employeesDao;

	@Autowired
	private DepartmentsDao departmentsDao;

	@Test
	public void createEmployeeTest() {
		Employee employee = new Employee();
		employee.setName("CreateTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(null);
		employeesDao.create(employee);
		assertEquals(employee.getName(), employeesDao.read(employee.getId()).getName());
	}

	@Test
	public void readEmployeeTest() {
		Employee employee = new Employee();
		employee.setName("ReadTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(null);
		employeesDao.create(employee);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Employee readEmployee = employeesDao.read(employee.getId());
		assertEquals(employee.getId(), readEmployee.getId());
		assertEquals(employee.getName(), readEmployee.getName());
		assertEquals(dateFormat.format(employee.getDob()), dateFormat.format(readEmployee.getDob()));
		assertEquals(employee.getSalary(), readEmployee.getSalary());
		assertEquals(employee.getDepartmentId(), readEmployee.getDepartmentId());
	}

	@Test
	public void updateEmployeeTest() {
		Employee employee = new Employee();
		employee.setName("UpdateTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(null);
		employeesDao.create(employee);
		employee.setName("newVlaueTest");
		employeesDao.update(employee);
		assertEquals(employee.getName(), employeesDao.read(employee.getId()).getName());
	}

	@Test
	public void deleteEmployeeTest() {
		Employee employee = new Employee();
		employee.setName("DeleteTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(null);
		employeesDao.create(employee);
		employeesDao.delete(employee.getId());
		assertNull(employeesDao.read(employee.getId()));
	}

	@Test
	public void findAllViewEmployeesTest() {
		Department department = new Department();
		department.setName("FindAllViewTest");
		departmentsDao.create(department);
		Employee employee = new Employee();
		employee.setName("FindAllViewTest");
		employee.setDob(new Date());
		employee.setSalary(1000);
		employee.setDepartmentId(department.getId());
		employeesDao.create(employee);
		List<EmployeeView> employeesList = employeesDao.findAllView();
		assert (employeesList.size() > 0);
		for (EmployeeView employeeView : employeesList) {
			if (employeeView.getDepartmentId() == department.getId()) {
				assertEquals(employeeView.getDepartmentName(), department.getName());
			}
		}
	}

	@Test
	public void findAllByDepartmentIdAndDOBEmployeesTest() throws ParseException {
		Department department = new Department();
		department.setName("FindAllByDepartmentIdAndDOBTest");
		departmentsDao.create(department);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date startDate = dateFormat.parse("01.01.2001");
		Date dobDate = dateFormat.parse("02.01.2001");
		Date endDate = dateFormat.parse("03.01.2001");
		Employee employee = new Employee();
		employee.setName("FindAllByDepartmentIdAndDOBTest");
		employee.setDob(dobDate);
		employee.setSalary(1000);
		employee.setDepartmentId(department.getId());
		employeesDao.create(employee);
		List<EmployeeView> employeesList = employeesDao.findAllByDepartmentIdAndDOB(department.getId(), null, null);
		assertEquals(employee.getName(), employeesList.get(0).getName());
		employeesList = employeesDao.findAllByDepartmentIdAndDOB(department.getId(), dobDate, null);
		assertEquals(employee.getName(), employeesList.get(0).getName());
		employeesList = employeesDao.findAllByDepartmentIdAndDOB(department.getId(), startDate, endDate);
		assertEquals(employee.getName(), employeesList.get(0).getName());
	}

}
