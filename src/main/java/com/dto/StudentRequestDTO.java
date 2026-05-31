package com.dto;

import java.util.List;

import com.model.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

	private String userName;
	
	private String password;
	
	private String email;
	
	private String phone;
	
	private int age;
	
	private List<Authority> authorities;
}
