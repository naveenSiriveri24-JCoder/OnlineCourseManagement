package com.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.dto.CourseRequestDto;
import com.dto.CourseResponseDto;

@Service
public interface CourseService {

	void addCourse(CourseRequestDto courseRequestDto);

	List<CourseResponseDto> getAllCourses();
	
	List<CourseResponseDto> getCoursesByName(String courseName);

	void deleteCourseByCourseCode(String courseCode);
	
	CourseResponseDto findCourseByCourseCode(String courseCode);
	
	CourseResponseDto updateCourseByCourseCode(String courseCode,CourseRequestDto courseRequestDto);

	List<CourseResponseDto> addMultipleCourses(List<CourseRequestDto> courseRequestDtoList);
}
