package by.test.rest.service.employees;

import java.util.Date;
import java.util.List;

import by.test.rest.model.employee.Employee;
import by.test.rest.model.employee.EmployeeView;

/**
 * Interface service for employees
 * 
 * @author Andrey Gorbachov
 *
 */

public interface EmployeesService {

	/**
	 * Get employee by id
	 * 
	 * @param id
	 * @return employee
	 */
	Employee get(Long id);

	/**
	 * Create new employee
	 * 
	 * @param employee
	 */
	void create(Employee employee);

	/**
	 * Update employee
	 * 
	 * @param employee
	 */
	void update(Employee employee);

	/**
	 * Delete employee by id
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Get all employees for view
	 * 
	 * @return list of employees
	 */
	List<EmployeeView> getAllView();

	/**
	 * Search employees by department_id and date of birth
	 * 
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @return list of employees
	 */
	List<EmployeeView> searchAllByDepartmentIdAndDOB(Long id, Date startDate, Date endDate);
}
