package spring.boot.project.demo2.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.project.demo2.validator.ValidEmail;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	
	@Id
	@Column(name = "email")
	@ValidEmail
	private String email;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "first_name")
	private String firstName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Column(name = "last_name")
	private String lastName;
	
	@JsonIgnore
	@NotNull(message = "is required")
	@ManyToOne(cascade =  { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "zipcode")
	private Address address;
	
//	@JsonBackReference(value = "admins")
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
	
	@NotNull(message = "is required")
	@Column(name = "date_of_birth")
	private String dateOfBirth;

}
