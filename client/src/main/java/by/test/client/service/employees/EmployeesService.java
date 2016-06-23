package by.test.client.service.employees;

import org.springframework.web.client.HttpClientErrorException;

import by.test.client.model.employee.Employee;
import by.test.client.web.mvc.ResponseForm;
import by.test.client.web.mvc.departments.SearchForm;

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
	 * @return response form
	 * @throws HttpClientErrorException
	 */
	ResponseForm get(Long id);

	/**
	 * Save employee
	 * 
	 * @param employee
	 * @return response form
	 * @throws HttpClientErrorException
	 */
	ResponseForm save(Employee employee);

	/**
	 * Delete employee by id
	 * 
	 * @param id
	 * @return response form
	 * @throws HttpClientErrorException
	 */
	ResponseForm delete(Long id);

	/**
	 * Get all employees
	 * 
	 * @return response form
	 * @throws HttpClientErrorException
	 */
	ResponseForm getAllView();

	/**
	 * Search employees by department_id and date of birth
	 * 
	 * @param id
	 * @param searchForm
	 * @return response form
	 * @throws HttpClientErrorException
	 */
	ResponseForm getAllByDepartmentIdAndDOB(Long id, SearchForm searchForm);
}
