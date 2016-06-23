package by.test.rest.model.employee;

import java.util.Date;

import by.test.rest.model.NamedEntity;

/**
 * Class entity to work with the "employees" table
 * 
 * @author Andrey Gorbachov
 *
 */

public class Employee extends NamedEntity {

	private static final long serialVersionUID = 1L;

	private Date dob;
	private Integer salary;
	private Long departmentId;

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
