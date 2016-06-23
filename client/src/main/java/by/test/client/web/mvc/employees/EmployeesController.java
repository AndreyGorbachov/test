package by.test.client.web.mvc.employees;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.test.client.model.employee.Employee;
import by.test.client.service.departments.DepartmentsService;
import by.test.client.service.employees.EmployeesService;
import by.test.client.web.mvc.ResponseForm;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@Controller
public class EmployeesController {

	private static final String EMPLOYEES = "employees";
	private static final String SAVE_EMPLOYEE = "save_employee";

	@Autowired
	private EmployeesService employeesService;

	@Autowired
	private DepartmentsService departmentsService;

	@Autowired
	private EmployeeValidator employeeValidator;

	@InitBinder("employeeForm")
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/employees.html", method = RequestMethod.GET)
	public String getEmployees(Model model) {
		ResponseForm form = employeesService.getAllView();
		if (form.getError() != null) {
			model.addAttribute("error", form.getError());
		}
		model.addAttribute("employeesList", form.getData());
		return EMPLOYEES;
	}

	@RequestMapping(value = "/add_employee.html", method = RequestMethod.GET)
	public String addEmployee(Model model) {
		ResponseForm form = departmentsService.getAllView();
		if (form.getError() != null) {
			model.addAttribute("error", form.getError());
			return EMPLOYEES;
		}
		model.addAttribute("employeeForm", new Employee());
		model.addAttribute("departmentsList", form.getData());
		return SAVE_EMPLOYEE;
	}

	@RequestMapping(value = "/edit_employee.html", method = RequestMethod.GET)
	public String editEmployee(@RequestParam("id") Long id, Model model) {
		ResponseForm getEmployeeForm = employeesService.get(id);
		ResponseForm getAllDepartmentsForm = departmentsService.getAllView();
		if (getEmployeeForm.getError() != null || getAllDepartmentsForm.getError() != null) {
			model.addAttribute("error", getEmployeeForm.getError() + "\n" + getAllDepartmentsForm.getError());
			return EMPLOYEES;
		}
		model.addAttribute("employeeForm", getEmployeeForm.getData());
		model.addAttribute("departmentsList", getAllDepartmentsForm.getData());
		return SAVE_EMPLOYEE;
	}

	@RequestMapping(value = "/save_employee.html", method = RequestMethod.POST)
	public String saveEmployee(@Valid @ModelAttribute("employeeForm") Employee employee, BindingResult result,
			Model model) {
		employeeValidator.validate(employee, result);
		if (result.hasErrors()) {
			model.addAttribute("employeeForm", employee);
			ResponseForm getAllDepartmentsForm = departmentsService.getAllView();
			if (getAllDepartmentsForm.getError() != null) {
				model.addAttribute("error", getAllDepartmentsForm.getError());
			}
			model.addAttribute("departmentsList", getAllDepartmentsForm.getData());
			return SAVE_EMPLOYEE;
		}
		ResponseForm saveEmployeeForm = employeesService.save(employee);
		if (saveEmployeeForm.getError() != null) {
			model.addAttribute("error", saveEmployeeForm.getError());
			return SAVE_EMPLOYEE;
		}
		return "redirect:/employees.html";
	}

	@RequestMapping(value = "/delete_employee.html", method = RequestMethod.GET)
	public String deleteEmployee(@RequestParam("id") Long id, Model model) {
		ResponseForm form = employeesService.delete(id);
		if (form.getError() != null) {
			model.addAttribute("error", form.getError());
			return EMPLOYEES;
		}
		return "redirect:/employees.html";
	}

}
