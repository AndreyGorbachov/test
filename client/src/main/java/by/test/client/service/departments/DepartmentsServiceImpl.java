package by.test.client.service.departments;

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
import by.test.client.model.department.Department;
import by.test.client.model.department.DepartmentView;
import by.test.client.web.mvc.ResponseForm;

/**
 * Class service for departments
 * 
 * @author Andrey Gorbachov
 *
 */
@Service
public class DepartmentsServiceImpl implements DepartmentsService {

	private static final String URI = "http://localhost:8080/rest/departments";
	private static final String P_URI = "http://localhost:8080/rest/departments/{id}";

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public ResponseForm get(Long id) {
		ResponseForm form = new ResponseForm();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id.toString());
		try {
			ResponseEntity<Department> response = restTemplate.exchange(P_URI, HttpMethod.GET, HttpEntity.EMPTY,
					Department.class, params);
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
	public ResponseForm save(Department department) {
		HttpEntity<Department> departmentEntity = new HttpEntity<>(department);
		ResponseForm form = new ResponseForm();

		if (department.getId() == null) {
			try {
				ResponseEntity<?> response = restTemplate.exchange(URI, HttpMethod.POST, departmentEntity,
						String.class);
				if (response.getStatusCode() == HttpStatus.ACCEPTED) {
					form.setError("Error service");
				}
				return form;
			} catch (HttpClientErrorException e) {
				if (e.getMessage().contains("409 Conflict")) {
					form.setError("Department already exists");
				} else {
					form.setError("Service not available");
				}
				return form;
			}
		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", department.getId().toString());
			try {
				ResponseEntity<?> response = restTemplate.exchange(P_URI, HttpMethod.PUT, departmentEntity,
						String.class, params);
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
				form.setError("The department has links");
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
			ResponseEntity<List<DepartmentView>> response = restTemplate.exchange(URI, HttpMethod.GET, HttpEntity.EMPTY,
					new ParameterizedTypeReference<List<DepartmentView>>() {
					});
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
