package spring.boot.project.demo2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.project.demo2.dao.RoleRepository;
import spring.boot.project.demo2.dao.StudentRepository;
import spring.boot.project.demo2.dao.UserRepository;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.User;

@Service
public class StudentServiceImpl implements StudentService {
	
	private static Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student findById(String theRollNo) {
		Optional<Student> result = studentRepository.findById(theRollNo);
		
		Student theStudent = null;
		
		if (result.isPresent()) {
			theStudent = result.get();
		}
		else {
			// we didn't find the student
			throw new RuntimeException("Did not find student with roll number - " + theRollNo);
		}
		
		return theStudent;
	}

	@Override
	public void save(Student theStudent) {
		logger.info(theStudent.getDepartment().toString());
		User user = new User();
		user.setUserName(theStudent.getRollNumber());
		user.setPassword(theStudent.getDateOfBirth().replaceAll("-", ""));
		user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_STUDENT")));
		studentRepository.save(theStudent);
		userRepository.save(user);

	}

	@Override
	public void deleteById(String theRollNo) {
		studentRepository.deleteById(theRollNo);

	}

	@Override
	public List<Student> findByDepartment(Department theDepartment) {
		return studentRepository.findByDepartment(theDepartment);
		
	}

	@Override
	public Student addCourse(Student theStudent, Course theCourse, Department department) {
		if(theStudent.getDepartment().equals(department) && theCourse.getDepartment().equals(department))
		{
			List<Course> courses = theStudent.getCourses();
			courses.add(theCourse);
			theStudent.setCourses(courses);
			studentRepository.save(theStudent);
		}
		else if((!theStudent.getDepartment().equals(department)) && theCourse.getDepartment().equals(department)) {
			theStudent.setDepartment(department);
			List<Course> courses = new ArrayList<Course>();
			courses.add(theCourse);
			theStudent.setCourses(courses);
			studentRepository.save(theStudent);
		} 
		return theStudent;
	}

	@Override
	public Student deleteCourse(Student theStudent, Course theCourse, Department department) {
		if(theStudent.getDepartment().equals(department) && theCourse.getDepartment().equals(department)) {
			List<Course> courses = theStudent.getCourses();
			courses.remove(theCourse);
			theStudent.setCourses(courses);
			studentRepository.save(theStudent);
		}
		return theStudent;
	}

}
