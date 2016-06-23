package by.test.client.service.departments;

import by.test.client.model.department.Department;
import by.test.client.web.mvc.ResponseForm;

/**
 * Interface service for departments
 * 
 * @author Andrey Gorbachov
 *
 */

public interface DepartmentsService {

	/**
	 * Get department by id
	 * 
	 * @param id
	 * @return response form
	 */
	ResponseForm get(Long id);

	/**
	 * Save department
	 * 
	 * @param department
	 * @return response form
	 */
	ResponseForm save(Department department);

	/**
	 * Delete department by id
	 * 
	 * @param id
	 * @return response form
	 */
	ResponseForm delete(Long id);

	/**
	 * Get all departments
	 * 
	 * @return response data
	 */
	ResponseForm getAllView();

}
