package by.test.client.web.mvc.departments;

import java.util.Date;

/**
 * Form search the employees by department id and date of birth
 * 
 * @author Andrey Gorbachov
 *
 */

public class SearchForm {

	private Long departmentId;
	private boolean period;
	private Date startDate;
	private Date endDate;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public boolean isPeriod() {
		return period;
	}

	public void setPeriod(boolean period) {
		this.period = period;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
