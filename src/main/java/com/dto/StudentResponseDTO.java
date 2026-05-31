package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {

	private long studentId;
	
	private String studentCode;
	
	private String userName;
	
	private String email;
	
	private String phone;
	
	private int age;
}
