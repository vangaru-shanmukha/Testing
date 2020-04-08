package spring.boot.project.demo2.service;

import java.util.List;

import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.User;

public interface StudentService {

	public List<Student> findAll();
	
	public Student findById(String theRollNo);
	
	public void save(Student theStudent);
	
	public void deleteById(String theRollNo);
	
	public List<Student> findByDepartment(Department theDepartment);
	
	public Student addCourse(Student theStudent,Course theCourse, Department department);
	
	public Student deleteCourse(Student theStudent,Course theCourse, Department department);
	
}
