package spring.boot.project.demo2.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String departmentName;

//	@JsonManagedReference(value = "students")
	@JsonIgnore
	@OneToMany(mappedBy = "department", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<Student> students;
	
	@JsonIgnore
//	@JsonManagedReference(value = "courses")
	@OneToMany(mappedBy = "department", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<Course> courses;

	@JsonIgnore
//	@JsonManagedReference(value = "admins")
	@OneToMany(mappedBy = "department", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<Admin> admins;

	@JsonIgnore
//	@JsonManagedReference(value = "teachers")
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "belongsto", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "email"))
	private List<Teacher> teachers;

	public Department(String dname) {
		departmentName = dname;
	}
	
	public Department(int id,String dname) {
		this.id = id;
		departmentName = dname;
	}

	public Department(String dname, List<Student> students) {
		departmentName = dname;
		this.students = students;
	}

}
