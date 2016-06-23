package by.test.rest.dao;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.test.rest.model.department.Department;
import by.test.rest.model.department.DepartmentView;
import by.test.rest.model.employee.Employee;

/**
 * @author Andrey Gorbachov
 *
 */
@ContextConfiguration(locations = "classpath:config/application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentsDaoTest {

	@Autowired
	private DepartmentsDao departmentsDao;

	@Autowired
	private EmployeesDao employeesDao;

	@Test
	public void createDepartmentTest() {
		Department department = new Department();
		department.setName("CreateTest");
		departmentsDao.create(department);
		assertEquals(department.getName(), departmentsDao.read(department.getId()).getName());
	}

	@Test
	public void readDepartmentTest() {
		Department department = new Department();
		department.setName("ReadTest");
		departmentsDao.create(department);
		Department readDepartment = departmentsDao.read(department.getId());
		assertEquals(department.getId(), readDepartment.getId());
		assertEquals(department.getName(), readDepartment.getName());
	}

	@Test
	public void updateDepartmentTest() {
		Department department = new Department();
		department.setName("UpdateTest");
		departmentsDao.create(department);
		department.setName("newVlaueTest");
		departmentsDao.update(department);
		assertEquals(department.getName(), departmentsDao.read(department.getId()).getName());
	}

	@Test
	public void deleteDepartmentTest() {
		Department department = new Department();
		department.setName("DleleteTest");
		departmentsDao.create(department);
		departmentsDao.delete(department.getId());
		assertEquals(null, departmentsDao.read(department.getId()));
	}

	@Test
	public void findAllViewDepartmentsTest() {
		Department departmentOne = new Department();
		departmentOne.setName("One");
		departmentsDao.create(departmentOne);
		Employee employeeOne = new Employee();
		employeeOne.setName("One");
		employeeOne.setDob(new Date());
		employeeOne.setSalary(1000);
		employeeOne.setDepartmentId(departmentOne.getId());
		employeesDao.create(employeeOne);
		Department departmentTwo = new Department();
		departmentTwo.setName("Two");
		departmentsDao.create(departmentTwo);
		Employee employeeTwo = new Employee();
		employeeTwo.setName("Two");
		employeeTwo.setDob(new Date());
		employeeTwo.setSalary(1200);
		employeeTwo.setDepartmentId(departmentTwo.getId());
		employeesDao.create(employeeTwo);
		Employee employeeThree = new Employee();
		employeeThree.setName("Three");
		employeeThree.setDob(new Date());
		employeeThree.setSalary(1100);
		employeeThree.setDepartmentId(departmentTwo.getId());
		employeesDao.create(employeeThree);
		List<DepartmentView> departmentsList = departmentsDao.findAllView();
		assert (departmentsList.size() > 0);
		Integer avgSalaryDepartmentTwo = (employeeTwo.getSalary() + employeeThree.getSalary()) / 2;
		for (DepartmentView departmentView : departmentsList) {
			if (departmentView.getId() == departmentOne.getId()) {
				assertEquals(departmentView.getSalary(), employeeOne.getSalary());
			}
			if (departmentView.getId() == departmentTwo.getId()) {
				assertEquals(departmentView.getSalary(), avgSalaryDepartmentTwo);
			}
		}
	}
}
