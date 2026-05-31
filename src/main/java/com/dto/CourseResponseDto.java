package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {

	private long courseId;
	
	private String courseCode;
	
	private String courseName;

	private String instructor;

	private double price;

	private double duration;

}
