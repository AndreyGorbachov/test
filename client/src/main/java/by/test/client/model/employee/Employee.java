package by.test.client.model.employee;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import by.test.client.model.NamedEntity;

/**
 * Class entity to work with the "employees" table
 * 
 * @author Andrey Gorbachov
 *
 */

public class Employee extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Past
	private Date dob;

	@NumberFormat
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

	public String getDobDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String dobDate = dateFormat.format(dob);
		return dobDate;
	}

}
