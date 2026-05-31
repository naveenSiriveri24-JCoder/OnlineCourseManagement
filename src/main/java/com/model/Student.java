package com.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long studentId;
	
	@Column(unique = true)
	private String studentCode;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String phone;
	
	@Column(nullable = false)
	private int age;
	
	@OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinTable(name = "student_authorities",
					joinColumns = {@JoinColumn(name = "student_id")},
					inverseJoinColumns = {@JoinColumn(name = "authority_id")})
	private List<Authority> authorities;
}
