package spring.boot.project.demo2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import spring.boot.project.demo2.dao.CourseRepository;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTests {

	@Mock
	private CourseRepository courseRepository;
	
	@InjectMocks
	private CourseServiceImpl courseService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindAll() {
		Department department = new Department(1,"Computer Science");
		Course course = new Course(1,"Java",department);
		doReturn(Arrays.asList(course)).when(courseRepository).findAll();
		List<Course> actualCourses = courseService.findAll();
		assertThat(actualCourses).isEqualTo(Arrays.asList(course));
	}
	
	@Test
	public void testFindById() {
		Department department = new Department(1,"Computer Science");
		Course course = new Course(1,"Java",department);
		Mockito.when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
		Course actualCourse = courseService.findById(course.getId());
		assertThat(actualCourse).isEqualTo(course);
	}
	
	@Test
	public void testDelete() {
		Department department = new Department(1,"Computer Science");
		Course course = new Course(1,"Java",department);
		courseService.deleteById(course.getId());
		verify(courseRepository,times(1)).deleteById(course.getId());
	}
	
	@Test
	public void testSave() {
		Department department = new Department(1,"Computer Science");
		Course course = new Course(1,"Java",department);
		courseService.save(course);
		verify(courseRepository,times(1)).save(course);
	}
	
	@Test
	public void testFindByDepartment() {
		Department department = new Department(1,"Computer Science");
		Course course = new Course(1,"Java",department);
		doReturn(Arrays.asList(course)).when(courseRepository).findByDepartment(department);
		List<Course> actualCourse = courseService.findByDepartment(department);
		assertThat(actualCourse).isEqualTo(Arrays.asList(course));
	}
}
