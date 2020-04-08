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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("inspection")
public class Student{
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "last_name")
	private String lastName;
	
//	@JsonBackReference(value = "students")
	@JsonIgnore
	@NotNull(message = "is required")
	@ManyToOne(cascade =  { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "department_id")
	private Department department;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "house_number")
	private String houseNumber;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "street")
	private String street;
	
	@JsonIgnore
	@NotNull(message = "is required")
	@ManyToOne(cascade =  { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "zipcode")
	private Address address;
	
	@Id
	@Column(name = "roll_number")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String rollNumber;
	
	@NotNull(message = "is required")
	@Column(name = "date_of_birth")
	private String dateOfBirth;

//	@JsonBackReference(value = "courses")
	@JsonIgnore
	@NotNull(message = "is required")
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name = "enrolls",
	joinColumns = @JoinColumn(name = "roll_number"),
	inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses;
	

}
