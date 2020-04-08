package spring.boot.project.demo2.dao;

import java.util.List;

import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Teacher;

public interface TeacherRepositoryCustom {
	
	public List<Teacher> findByDepartment(Department department);

}
