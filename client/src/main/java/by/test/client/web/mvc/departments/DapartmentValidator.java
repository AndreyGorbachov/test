package by.test.client.web.mvc.departments;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import by.test.client.model.department.Department;

/**
 * Class validator for department
 * 
 * @author Andrey Gorbachov
 *
 */
@Component
public class DapartmentValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Department.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Department department = (Department) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Name must not be empty");
		if (department.getName().length() > 50) {
			errors.rejectValue("name", "name.tooLong", "Name must not more than 50 characters.");
		}

	}

}
