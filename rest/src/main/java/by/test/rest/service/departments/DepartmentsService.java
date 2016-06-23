package by.test.rest.service.departments;

import java.util.List;

import by.test.rest.model.department.Department;
import by.test.rest.model.department.DepartmentView;

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
	 * @return department
	 */
	Department get(Long id);

	/**
	 * Create new department
	 * 
	 * @param department
	 */
	void create(Department department);

	/**
	 * Update department
	 * 
	 * @param department
	 */
	void update(Department department);

	/**
	 * Delete department by id
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Get all departments for view
	 * 
	 * @return list of departments
	 */
	List<DepartmentView> getAllView();

}
