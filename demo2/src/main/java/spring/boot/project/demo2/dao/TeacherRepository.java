package spring.boot.project.demo2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.project.demo2.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String>,TeacherRepositoryCustom {

}
