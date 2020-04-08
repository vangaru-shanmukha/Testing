package spring.boot.project.demo2.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
public class Course {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@JsonIgnore
//	@JsonBackReference(value = "courses")
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "department_id")
	private Department department;
	
	@JsonIgnore
//	@JsonManagedReference(value = "courses")
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name = "enrolls",
	joinColumns = @JoinColumn(name = "course_id"),
	inverseJoinColumns = @JoinColumn(name = "roll_number"))
	private List<Student> students;
	
	@JsonIgnore
//	@JsonManagedReference(value = "course")
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "teaches", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "email"))
	private List<Teacher> teachers;
	
	public Course(String name,Department department) {
		this.name = name;
		this.department = department;
	}
	
	public Course(int id,String name,Department department) {
		this.id = id;
		this.name = name;
		this.department = department;
	}

}
