package spring.boot.project.demo2.service;

import java.util.List;

import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.Teacher;

public interface TeacherService {

	public List<Teacher> findAll();

	public Teacher findById(String email);

	public void save(Teacher theTeacher);

	public void deleteById(String email);
	
	public List<Teacher> findByDepartment(Department department);

	public Teacher deleteDepartment(Teacher theTeacher, Department theDepartment);

	public Teacher deleteCourse(Teacher theTeacher, Course theCourse);

	public Teacher addDepartment(Teacher teacher, Department department);

	public Teacher addCourse(Teacher teacher, Course course);

	public Student addStudent(Student theStudent, Course course);

	public Student deleteStudent(Student theStudent, Course course);
}
