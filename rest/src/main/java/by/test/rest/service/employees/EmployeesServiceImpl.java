package by.test.rest.service.employees;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.test.rest.dao.EmployeesDao;
import by.test.rest.model.employee.Employee;
import by.test.rest.model.employee.EmployeeView;

/**
 * Class service for employees
 * 
 * @author Andrey Gorbachov
 *
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

	@Autowired
	private EmployeesDao employeesDao;

	public Employee get(Long id) {
		return employeesDao.read(id);
	}

	public void create(Employee employee) {
		employeesDao.create(employee);
	}

	public void update(Employee employee) {
		employeesDao.update(employee);
	}

	public void delete(Long id) {
		employeesDao.delete(id);
	}

	public List<EmployeeView> getAllView() {
		return employeesDao.findAllView();
	}

	public List<EmployeeView> searchAllByDepartmentIdAndDOB(Long id, Date startDate, Date endDate) {
		return employeesDao.findAllByDepartmentIdAndDOB(id, startDate, endDate);
	}

}
