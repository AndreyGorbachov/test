package by.test.client.web.mvc.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.test.client.service.departments.DepartmentsService;
import by.test.client.web.mvc.ResponseForm;

@Controller
public class MainController {

	@Autowired
	private DepartmentsService departmentsService;

	@RequestMapping(value = "/main.html", method = RequestMethod.GET)
	public String getMain(Model model) {
		ResponseForm form = departmentsService.getAllView();
		if (form.getError() != null) {
			model.addAttribute("error", form.getError());
		}
		model.addAttribute("departmentsList", form.getData());
		return "main";
	}

}
