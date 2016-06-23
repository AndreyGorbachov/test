package by.test.client.web.mvc.employees;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import by.test.client.model.employee.Employee;

/**
 * Class validator for employee
 * 
 * @author Andrey Gorbachov
 *
 */
@Component
public class EmployeeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Employee employee = (Employee) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Name must not be empty");
		if (employee.getName() != null) {
			if (employee.getName().length() > 50) {
				errors.rejectValue("name", "name.tooLong", "Name must not more than 50 characters.");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", "dob.empty", "Date of birth must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salary", "salary.empty", "Salary must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departmentId", "department.empty",
				"Department must not be empty");
	}

}
