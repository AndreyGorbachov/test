package by.test.client.service.employees;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import by.test.client.model.employee.Employee;
import by.test.client.model.employee.EmployeeView;
import by.test.client.web.mvc.ResponseForm;
import by.test.client.web.mvc.departments.SearchForm;

/**
 * Class service for employees
 * 
 * @author Andrey Gorbachov
 *
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

	private static final String URI = "http://localhost:8080/rest/employees";
	private static final String P_URI = "http://localhost:8080/rest/employees/{id}";
	private static final String P_URI_SEARCH = "http://localhost:8080/rest/employees/{id}/search";

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public ResponseForm get(Long id) {
		ResponseForm form = new ResponseForm();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id.toString());
		try {
			ResponseEntity<Employee> response = restTemplate.exchange(P_URI, HttpMethod.GET, HttpEntity.EMPTY,
					Employee.class, params);
			if (response.getStatusCode() == HttpStatus.OK) {
				form.setData(response.getBody());
			}
			return form;
		} catch (HttpClientErrorException e) {
			form.setError("Service not available");
			return form;
		}
	}

	@Override
	public ResponseForm save(Employee employee) {
		HttpEntity<Employee> employeeEntity = new HttpEntity<>(employee);
		ResponseForm form = new ResponseForm();
		if (employee.getId() == null) {
			try {
				ResponseEntity<?> response = restTemplate.exchange(URI, HttpMethod.POST, employeeEntity, String.class);
				if (response.getStatusCode() == HttpStatus.ACCEPTED) {
					form.setError("Error service");
				}
				return form;
			} catch (HttpClientErrorException e) {
				if (e.getMessage().contains("409 Conflict")) {
					form.setError("employee already exists");
				} else {
					form.setError("Service not available");
				}
				return form;
			}
		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", employee.getId().toString());
			try {
				ResponseEntity<?> response = restTemplate.exchange(P_URI, HttpMethod.PUT, employeeEntity, String.class,
						params);
				if (response.getStatusCode() == HttpStatus.ACCEPTED) {
					form.setError("Error service");
				}
				return form;
			} catch (HttpClientErrorException e) {
				form.setError("Service not available");
				return form;
			}
		}
	}

	@Override
	public ResponseForm delete(Long id) {
		ResponseForm form = new ResponseForm();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id.toString());
		try {
			ResponseEntity<?> response = restTemplate.exchange(P_URI, HttpMethod.DELETE, HttpEntity.EMPTY, String.class,
					params);
			if (response.getStatusCode() == HttpStatus.ACCEPTED) {
				form.setError("The employee has links");
			}
			return form;
		} catch (HttpClientErrorException e) {
			form.setError("Service not available");
			return form;
		}
	}

	@Override
	public ResponseForm getAllView() {
		ResponseForm form = new ResponseForm();
		try {
			ResponseEntity<List<EmployeeView>> response = restTemplate.exchange(URI, HttpMethod.GET, HttpEntity.EMPTY,
					new ParameterizedTypeReference<List<EmployeeView>>() {
					});
			form.setData(response.getBody());
			return form;
		} catch (HttpClientErrorException e) {
			form.setError("Service not available");
			return form;
		}
	}

	@Override
	public ResponseForm getAllByDepartmentIdAndDOB(Long id, SearchForm searchForm) {
		String uri = P_URI_SEARCH;
		ResponseForm form = new ResponseForm();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id.toString());
		if (searchForm.getStartDate() != null) {
			uri += "?startDate=" + dateFormat.format(searchForm.getStartDate());
			if (searchForm.getEndDate() != null) {
				uri += "&endDate=" + dateFormat.format(searchForm.getEndDate());
			}
		}
		try {
			ResponseEntity<List<EmployeeView>> response = restTemplate.exchange(uri, HttpMethod.GET, HttpEntity.EMPTY,
					new ParameterizedTypeReference<List<EmployeeView>>() {
					}, params);
			form.setData(response.getBody());
			return form;
		} catch (HttpClientErrorException e) {
			form.setError("Service not available");
			return form;
		}
	}

	public RestTemplate getRestTemplate() {
		return this.restTemplate;
	}
}
