package spring.boot.project.demo2.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.boot.project.demo2.dao.RoleRepository;
import spring.boot.project.demo2.entity.Address;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Role;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.User;
import spring.boot.project.demo2.service.AddressService;
import spring.boot.project.demo2.service.CourseService;
import spring.boot.project.demo2.service.DepartmentService;
import spring.boot.project.demo2.service.StudentService;
import spring.boot.project.demo2.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private int theDepartmentId = Integer.MAX_VALUE;
	
	private static Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@GetMapping("/list")
	public String listAllStudents(@RequestParam("department") int departmentId, Model theModel) {
		theDepartmentId = departmentId;
		Department department = departmentService.findById(departmentId);
		List<Student> students = studentService.findByDepartment(department);
		theModel.addAttribute("students", students);
		return "students/list-students";
	}
	
	@GetMapping("/courses")
	public String getCourses(@RequestParam("rollNumber") String theRollNumber, Model theModel) {
		Student theStudent = studentService.findById(theRollNumber);
		List<Course> tempCourses = theStudent.getCourses();
		List<Course> theCourses = departmentService.findById(theStudent.getDepartment().getId()).getCourses();
		theCourses.removeAll(tempCourses);
		theModel.addAttribute("student", theStudent);
		theModel.addAttribute("courses", theCourses);
		return "students/list-courses";
	}
	
	@GetMapping("/enroll")
	public String enrollCourse(@RequestParam("student") String theRollNumber,
							   @RequestParam("course") int theCourseId, Model theModel) {
		Student theStudent = studentService.findById(theRollNumber);
		Course theCourse = courseService.findById(theCourseId);
		List<Course> courses = theStudent.getCourses();
		courses.add(theCourse);
		theStudent.setCourses(courses);
		studentService.save(theStudent);
		return "confirmation";
	}
	@GetMapping("/addStudent")
	public String addStudent(Model theModel) {
		List<Department> departments = Arrays.asList(departmentService.findById(theDepartmentId));
		List<Address> addresses = addressService.findAll();
		List<Course> courses = courseService.findByDepartment(departments.get(0));
		theModel.addAttribute("student", new Student());
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		theModel.addAttribute("courses", courses);
		return "students/add-student";
	}
	
	@PostMapping("/processForm")
	public String processForm(@Valid @ModelAttribute("student") Student theStudent, BindingResult theBindingResult, Model theModel) {
		String rollNumber = theStudent.getRollNumber();
		List<Address> addresses = addressService.findAll();
		List<Department> departments = Arrays.asList(departmentService.findById(theDepartmentId));
		List<Course> courses = courseService.findByDepartment(departments.get(0));
		logger.info("Processing registration form for: " + rollNumber);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		theModel.addAttribute("courses", courses);
		// form validation
		if(theBindingResult.hasErrors()) {
			return "add-student";
		}
		
		// check the database if the student already exists
		Student existingStudent = null;
		try {
			existingStudent = studentService.findById(rollNumber);
		} catch (RuntimeException e) {
			// create student
			User user = new User();
			user.setUserName(rollNumber);
			String date = theStudent.getDateOfBirth().replaceAll("-", "");
			user.setPassword(date);
			user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_STUDENT")));
			userService.save(user);
			logger.info(user.toString());
			logger.info("user saved");
			studentService.save(theStudent);
		} finally {
			if (existingStudent != null) {
				theModel.addAttribute("addStudentError", "Student with " + rollNumber + " exists already");
				logger.info("Student already exists");
				theModel.addAttribute("student", new Student());
				return "add-student";
			}
		}
		logger.info("Successfully added!");
		return "confirmation";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("rollNumber") String theRollNumber, Model theModel) {
		
		Student theStudent = studentService.findById(theRollNumber);
		List<Address> addresses = addressService.findAll();
		List<Department> departments = Arrays.asList(departmentService.findById(theDepartmentId));
		List<Course> courses = courseService.findByDepartment(departments.get(0));
		List<Course> studentCourses = theStudent.getCourses();
		theModel.addAttribute("student", theStudent);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		theModel.addAttribute("studentCourses", studentCourses);
		theModel.addAttribute("courses", courses);
		return "students/update-form";
	}
	
	@PostMapping("/save")
	public String saveStudent(@Valid @ModelAttribute("student") Student theStudent, BindingResult theBindingResult, Model theModel) {
		theModel.addAttribute("student", theStudent);
		List<Address> addresses = addressService.findAll();
		List<Department> departments = Arrays.asList(departmentService.findById(theDepartmentId));
		List<Course> courses = courseService.findByDepartment(departments.get(0));
		List<Course> studentCourses = theStudent.getCourses();
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		theModel.addAttribute("studentCourses", studentCourses);
		theModel.addAttribute("courses", courses);
		
		if(theBindingResult.hasErrors()) {
			return "/students/update-form";
		}
		studentService.save(theStudent);
		return "confirmation";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("rollNumber") String theRollNumber) {
		
		studentService.deleteById(theRollNumber);
		
		userService.deleteById(theRollNumber);
		return "confirmation";
		
	}
}
