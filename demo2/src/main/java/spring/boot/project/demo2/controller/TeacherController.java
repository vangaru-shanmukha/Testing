package spring.boot.project.demo2.controller;

import java.util.ArrayList;
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
import spring.boot.project.demo2.entity.Teacher;
import spring.boot.project.demo2.entity.User;
import spring.boot.project.demo2.service.AddressService;
import spring.boot.project.demo2.service.CourseService;
import spring.boot.project.demo2.service.DepartmentService;
import spring.boot.project.demo2.service.StudentService;
import spring.boot.project.demo2.service.TeacherService;
import spring.boot.project.demo2.service.UserService;


@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private static Logger logger = LoggerFactory.getLogger(TeacherController.class);
	
	@GetMapping("/list")
	public String listAllStudents(Model theModel) {
		List<Teacher> teachers = teacherService.findAll();
		theModel.addAttribute("teachers", teachers);
		return "teachers/list-teachers";
	}
	
	@GetMapping("/courses")
	public String getDepartments(@RequestParam("id") String theEmail,Model theModel) {
		List<Course> theCourses = teacherService.findById(theEmail).getCourses();
		theModel.addAttribute("courses", theCourses);
		return "teachers/list-courses";
	}
	
	@GetMapping("/showFormForAddStudent")
	public String showFormForAddStudent(@RequestParam("department") int theDepartmentId,
										@RequestParam("course") int theCourseId,
										Model theModel) {
		
		logger.info("in form");
		Department theDepartment = departmentService.findById(theDepartmentId);
		Course theCourse = courseService.findById(theCourseId);
		List<Student> tempStudents = theDepartment.getStudents();
		List<Student> students = new ArrayList<Student>();
		for(Student tempStudent : tempStudents) {
			if(!tempStudent.getCourses().contains(theCourse)) {
				students.add(tempStudent);
			}
		}
		theModel.addAttribute("students", students);
		theModel.addAttribute("course", theCourse);
		return "teachers/add-student";
	}
	
	@PostMapping("/processFormForAddStudent")
	public String processFormForAddStudent(@RequestParam("rollNumber") String theRollNumber, @RequestParam("course") int theCourseId, Model theModel) {
		
		Student theStudent = studentService.findById(theRollNumber);
		Course theCourse = courseService.findById(theCourseId);
		Student student = studentService.findById(theStudent.getRollNumber());
		List<Course> courses = student.getCourses();
		courses.add(theCourse);
		student.setCourses(courses);
		studentService.save(student);
		return "confirmation";
	}
	
	@GetMapping("/addTeacher")
	public String addTeacher(Model theModel) {
		List<Department> departments = departmentService.findAll();
		List<Address> addresses = addressService.findAll();
		theModel.addAttribute("teacher", new Teacher());
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		List<Course> courses = courseService.findAll();
		theModel.addAttribute("courses", courses);
		return "teachers/add-teacher";
	}
	
	@PostMapping("/processForm")
	public String processForm(@Valid @ModelAttribute("teacher") Teacher theTeacher, BindingResult theBindingResult, Model theModel) {
		List<Department> departments = departmentService.findAll();
		List<Address> addresses = addressService.findAll();
		String email = theTeacher.getEmail();
		logger.info("Processing registration form for: " + email);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		// form validation
		if(theBindingResult.hasErrors()) {
			return "teachers/add-teacher";
		}
		
		// check the database if the teacher already exists
		Teacher existingStudent = null;
		try {
			existingStudent = teacherService.findById(email);
		} catch (RuntimeException e) {
			// create teacher
			User user = new User();
			user.setUserName(email);
			String date = theTeacher.getDateOfBirth().replaceAll("-", "");
			user.setPassword(date);
			user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_TEACHER")));
			userService.save(user);
			teacherService.save(theTeacher);
		} finally {
			if (existingStudent != null) {
				theModel.addAttribute("addTeacherError", "Teacher with " + email + " exists already");
				logger.info("Teacher already exists");
				theModel.addAttribute("teacher", new Teacher());
				return "teachers/add-teacher";
			}
		}
		logger.info("Successfully added!");
		return "confirmation";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("email") String theEmail, Model theModel) {
		List<Department> departments = departmentService.findAll();
		List<Address> addresses = addressService.findAll();
		Teacher theTeacher = teacherService.findById(theEmail);
		List<Course> teacherCourses = theTeacher.getCourses();
		List<Course> courses = courseService.findAll();
		List<Department> teacherDepartments = theTeacher.getDepartments();
		theModel.addAttribute("teacher", theTeacher);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		theModel.addAttribute("courses", courses);
		theModel.addAttribute("teacherCourses", teacherCourses);
		theModel.addAttribute("teacherDepartments", teacherDepartments);
		return "teachers/update-form";
	}
	
	@PostMapping("/save")
	public String saveStudent(@Valid @ModelAttribute("teacher") Teacher theTeacher, BindingResult theBindingResult, Model theModel) {
		
		List<Department> departments = departmentService.findAll();
		List<Address> addresses = addressService.findAll();
		List<Course> teacherCourses = theTeacher.getCourses();
		List<Course> courses = courseService.findAll();
		List<Department> teacherDepartments = theTeacher.getDepartments();
		theModel.addAttribute("teacher", theTeacher);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		theModel.addAttribute("courses", courses);
		theModel.addAttribute("teacherCourses", teacherCourses);
		theModel.addAttribute("teacherDepartments", teacherDepartments);
		theModel.addAttribute("departments", departments);
		theModel.addAttribute("addresses", addresses);
		
		if(theBindingResult.hasErrors()) {
			return "/teachers/update-form";
		}
		teacherService.save(theTeacher);
		return "confirmation";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("email") String theEmail) {
		
		teacherService.deleteById(theEmail);
		
		userService.deleteById(theEmail);
		return "confirmation";
		
	}
}
