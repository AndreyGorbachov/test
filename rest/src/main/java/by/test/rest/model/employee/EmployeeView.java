package by.test.rest.model.employee;

/**
 * Class entity to view the table "employees"
 * 
 * @author Andrey Gorbachov
 *
 */

public class EmployeeView extends Employee {

	private static final long serialVersionUID = 1L;

	private String departmentName;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
