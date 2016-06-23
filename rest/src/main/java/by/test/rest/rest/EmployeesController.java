package by.test.rest.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.test.rest.model.employee.Employee;
import by.test.rest.model.employee.EmployeeView;
import by.test.rest.service.employees.EmployeesService;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@RestController
@RequestMapping("/employees")
public class EmployeesController {

	@Autowired
	private EmployeesService employeesService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<EmployeeView> getAllViewEmployee() {
		return employeesService.getAllView();
	}

	@RequestMapping(value = "/{id}/search", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<EmployeeView> searchAllByDepartmentIdAndDOB(@PathVariable("id") Long id,
			@RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "endDate", required = false) Date endDate) {
		return employeesService.searchAllByDepartmentIdAndDOB(id, startDate, endDate);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<Employee>(employeesService.get(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		try {
			employeesService.create(employee);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().contains("UNIQUE")) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
		try {
			if (employeesService.get(id) == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				employeesService.update(employee);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
		try {
			employeesService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	}
}
