package by.test.rest.model.department;

/**
 * Class entity to view the table "departments"
 * 
 * @author Andrey Gorbachov
 *
 */

public class DepartmentView extends Department {

	private static final long serialVersionUID = 1L;

	private Integer salary;

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

}
