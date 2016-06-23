package by.test.rest.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.test.rest.model.department.Department;
import by.test.rest.model.department.DepartmentView;
import by.test.rest.service.departments.DepartmentsService;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@RestController
@RequestMapping("/departments")
public class DepartmentsController {

	@Autowired
	private DepartmentsService departmentsService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<DepartmentView> getAllDepartment() {
		return departmentsService.getAllView();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
		try {
			return new ResponseEntity<Department>(departmentsService.get(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createDepartment(@RequestBody Department department) {
		try {
			departmentsService.create(department);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().contains("UNIQUE")) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateDepartment(@PathVariable("id") Long id, @RequestBody Department department) {
		try {
			if (departmentsService.get(id) == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				departmentsService.update(department);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
		try {
			departmentsService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	}
}
