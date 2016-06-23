package by.test.rest.dao;

import java.util.List;

import by.test.rest.model.department.Department;
import by.test.rest.model.department.DepartmentView;

/**
 * Interface for DAO the table "departments"
 * 
 * @author Andrey Gorbachov
 *
 */

public interface DepartmentsDao extends Dao<Department, Long> {

	/**
	 * Find all departments for view
	 * 
	 * @return list of departments
	 */
	List<DepartmentView> findAllView();

}
