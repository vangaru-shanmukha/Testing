package spring.boot.project.demo2.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import spring.boot.project.demo2.dao.RoleRepository;
import spring.boot.project.demo2.dao.StudentRepository;
import spring.boot.project.demo2.dao.TeacherRepository;
import spring.boot.project.demo2.dao.UserRepository;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.Teacher;
import spring.boot.project.demo2.entity.User;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Override
	public List<Teacher> findAll() {
		return teacherRepository.findAll();
	}

	@Override
	public Teacher findById(String email) {
		Optional<Teacher> result = teacherRepository.findById(email);
		Teacher theTeacher = null;
		if(result.isPresent()) {
			theTeacher = result.get();
		}
		else {
			throw new RuntimeException("Did not find teacher with email: " + email);
		}
		return theTeacher;
	}

	@Override
	public void save(Teacher theTeacher) {
		User user = new User();
		user.setUserName(theTeacher.getEmail());
		user.setPassword(bcryptPasswordEncoder.encode(theTeacher.getDateOfBirth().replaceAll("-", "")));
		user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_TEACHER")));
		teacherRepository.save(theTeacher);
		userRepository.save(user);
	}

	@Override
	public void deleteById(String email) {
		teacherRepository.deleteById(email);
		userRepository.deleteById(email);

	}

	@Override
	public List<Teacher> findByDepartment(Department department) {
		return teacherRepository.findByDepartment(department);
	}

	@Override
	public Teacher deleteDepartment(Teacher theTeacher, Department theDepartment) {
		List<Department> departments = theTeacher.getDepartments();
		List<Course> courses = theTeacher.getCourses();
		List<Course> allCourses = theDepartment.getCourses();
		if(departments.contains(theDepartment)) {
			departments.remove(theDepartment);
			theTeacher.setDepartments(departments);
			courses.removeAll(allCourses);
			theTeacher.setCourses(courses);
			teacherRepository.save(theTeacher);
		}
		return theTeacher;
	}

	@Override
	public Teacher deleteCourse(Teacher theTeacher, Course theCourse) {
		List<Course> courses = theTeacher.getCourses();
		if(courses.contains(theCourse)) {
			courses.remove(theCourse);
			theTeacher.setCourses(courses);
			teacherRepository.save(theTeacher);
		}
		return theTeacher;
	}

	@Override
	public Teacher addDepartment(Teacher teacher, Department department) {
		List<Department> departments = teacher.getDepartments();
		departments.add(department);
		teacher.setDepartments(departments);
		save(teacher);
		return teacher;
	}

	@Override
	public Teacher addCourse(Teacher teacher, Course course) {
		if(teacher.getDepartments().contains(course.getDepartment())) {
			List<Course> courses = teacher.getCourses();
			courses.add(course);
			teacher.setCourses(courses);
			save(teacher);
		}
		return teacher;
	}

	@Override
	public Student addStudent(Student theStudent, Course course) {
		if(theStudent.getDepartment().equals(course.getDepartment())) {
			List<Course> courses = theStudent.getCourses();
			courses.add(course);
			theStudent.setCourses(courses);
			studentRepository.save(theStudent);
		}
		return theStudent;
	}

	@Override
	public Student deleteStudent(Student theStudent, Course course) {
		if(theStudent.getDepartment().equals(course.getDepartment()) && theStudent.getCourses().contains(course)) {
			List<Course> courses = theStudent.getCourses();
			courses.remove(course);
			theStudent.setCourses(courses);
			studentRepository.save(theStudent);
		}
		return theStudent;
		
	}

}
