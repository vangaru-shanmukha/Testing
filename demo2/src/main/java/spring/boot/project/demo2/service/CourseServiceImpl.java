package spring.boot.project.demo2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.project.demo2.dao.CourseRepository;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	@Override
	public Course findById(int theId) {
		Optional<Course> result = courseRepository.findById(theId);
		Course theCourse = null;
		if(result.isPresent()) {
			theCourse = result.get();
		}
		else {
			throw new RuntimeException("Did not find course with id : " + theId);
		}
		return theCourse;
	}

	@Override
	public void save(Course theCourse) {
		courseRepository.save(theCourse);

	}

	@Override
	public void deleteById(int theId) {
		courseRepository.deleteById(theId);
	}

	@Override
	public List<Course> findByDepartment(Department department) {
		return courseRepository.findByDepartment(department);
	}

}
