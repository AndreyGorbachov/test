package by.test.rest.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import by.test.rest.model.employee.Employee;
import by.test.rest.model.employee.EmployeeView;

/**
 * Interface for DAO the table "employees"
 * 
 * @author Andrey Gorbachov
 *
 */

public interface EmployeesDao extends Dao<Employee, Long> {

	/**
	 * Find all employees for view
	 * 
	 * @return list of employees
	 */
	List<EmployeeView> findAllView();

	/**
	 * Find employees by department id and date of birth
	 * 
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @return list of employees
	 */
	List<EmployeeView> findAllByDepartmentIdAndDOB(@Param("id") Long id, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

}
