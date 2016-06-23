package by.test.rest.service.departments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.test.rest.dao.DepartmentsDao;
import by.test.rest.model.department.Department;
import by.test.rest.model.department.DepartmentView;

/**
 * Class service for departments
 * 
 * @author Andrey Gorbachov
 *
 */
@Service
public class DepartmentsServiceImpl implements DepartmentsService {

	@Autowired
	private DepartmentsDao departmentsDao;

	public Department get(Long id) {
		return departmentsDao.read(id);
	}

	public void create(Department department) {
		departmentsDao.create(department);
	}

	public void update(Department department) {
		departmentsDao.update(department);
	}

	public void delete(Long id) {
		departmentsDao.delete(id);
	}

	public List<DepartmentView> getAllView() {
		return departmentsDao.findAllView();
	}

}
