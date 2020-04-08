package spring.boot.project.demo2.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Teacher;
import spring.boot.project.demo2.service.TeacherService;

@Repository
@Transactional( readOnly = true)
public class TeacherRepositoryCustomImpl implements TeacherRepositoryCustom {

	@Autowired
	private TeacherService teacherService;
	
	@Override
	public List<Teacher> findByDepartment(Department theDepartment) {
		
		List<Teacher> teachers = teacherService.findAll();
		List<Teacher> result = new ArrayList<Teacher>();
		for(Teacher teacher : teachers) {
			List<Department> departments = teacher.getDepartments();
			for(Department department : departments) {
				if(department.equals(theDepartment)) {
					result.add(teacher);
					break;
				}
			}
		}
		return result;
	}

}
