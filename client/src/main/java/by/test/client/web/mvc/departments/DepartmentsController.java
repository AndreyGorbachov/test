package by.test.client.web.mvc.departments;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import by.test.client.model.department.Department;
import by.test.client.service.departments.DepartmentsService;
import by.test.client.service.employees.EmployeesService;
import by.test.client.web.mvc.ResponseForm;

/**
 * 
 * @author Andrey Gorbachov
 *
 */
@Controller
public class DepartmentsController {

	private static final String DEPARTMENTS = "departments";
	private static final String VIEW_DEPARTMENTS = "view_department";
	private static final String SAVE_DEPARTMENT = "save_department";
	private static final String MAIN = "main";

	@Autowired
	private DepartmentsService departmentsService;

	@Autowired
	private EmployeesService employeesService;

	@Autowired
	private DapartmentValidator dapartmentValidator;

	@InitBinder("searchForm")
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/departments.html", method = RequestMethod.GET)
	public String getDepartments(Model model) {
		ResponseForm form = departmentsService.getAllView();
		if (form.getError() != null) {
			model.addAttribute("error", form.getError());
			return DEPARTMENTS;
		}
		model.addAttribute("departmentsList", form.getData());
		return DEPARTMENTS;
	}

	@RequestMapping(value = "/view_department.html", method = RequestMethod.GET)
	public String viewDepartment(@RequestParam("departmentId") Long departmentId,
			@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
		ResponseForm getDepartmentForm = departmentsService.get(departmentId);
		ResponseForm getAllEmployeesForm = employeesService.getAllByDepartmentIdAndDOB(departmentId, searchForm);
		if (getDepartmentForm.getError() != null || getAllEmployeesForm.getError() != null) {
			model.addAttribute("error", getDepartmentForm.getError() + "\n" + getAllEmployeesForm.getError());
			return MAIN;
		}
		model.addAttribute("department", getDepartmentForm.getData());
		model.addAttribute("employeesList", getAllEmployeesForm.getData());
		model.addAttribute("searchForm", searchForm);
		return VIEW_DEPARTMENTS;
	}

	@RequestMapping(value = "/add_department.html", method = RequestMethod.GET)
	public String addDepartment(Model model) {
		model.addAttribute("departmentForm", new Department());
		return SAVE_DEPARTMENT;
	}

	@RequestMapping(value = "/edit_department.html", method = RequestMethod.GET)
	public String editDepartment(@RequestParam("id") Long id, Model model) {
		ResponseForm form = departmentsService.get(id);
		if (form.getError() != null) {
			model.addAttribute("error", form.getError());
			return DEPARTMENTS;
		}
		model.addAttribute("departmentForm", form.getData());
		return SAVE_DEPARTMENT;
	}

	@RequestMapping(value = "/save_department.html", method = RequestMethod.POST)
	public String saveDepartment(@ModelAttribute("departmentForm") Department department, BindingResult result,
			Model model) {
		dapartmentValidator.validate(department, result);
		if (result.hasErrors()) {
			model.addAttribute("departmentForm", department);
			return SAVE_DEPARTMENT;
		}
		ResponseForm form = departmentsService.save(department);
		if (form.getError() != null) {
			model.addAttribute("departmentForm", department);
			model.addAttribute("error", form.getError());
			return SAVE_DEPARTMENT;
		}
		return "redirect:/departments.html";
	}

	@RequestMapping(value = "/delete_department.html", method = RequestMethod.GET)
	public String deleteDepartment(@RequestParam("id") Long id, Model model) {
		ResponseForm form = departmentsService.delete(id);
		if (form.getError() != null) {
			model.addAttribute("departmentsList", departmentsService.getAllView().getData());
			model.addAttribute("error", form.getError());
			return DEPARTMENTS;
		}
		return "redirect:/departments.html";
	}

}
