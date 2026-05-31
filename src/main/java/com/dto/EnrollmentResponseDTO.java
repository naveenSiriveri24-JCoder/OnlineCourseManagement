package com.dto;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponseDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long enrollmentId;
	
	private String studentName;
	
	private String courseName;
	
	private LocalDate enrollmentDate;
	
	private String status;
}
