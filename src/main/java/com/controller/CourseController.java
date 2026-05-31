package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CourseRequestDto;
import com.dto.CourseResponseDto;
import com.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	CourseService courseService;
	
	public CourseController(CourseService courseService) {
		super();
		this.courseService = courseService;
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addCourse(@RequestBody CourseRequestDto courseRequestDto) {
		courseService.addCourse(courseRequestDto);
		return new ResponseEntity<>("The "+courseRequestDto.getCourseName()+ " course added Successfully" , HttpStatus.CREATED);
	}
	
	@GetMapping("/get-all-courses")
	public ResponseEntity<List<CourseResponseDto>> getAllCourses(){
		List<CourseResponseDto> allCourses = courseService.getAllCourses();
		return new ResponseEntity<>(allCourses, HttpStatus.OK);
	}
	
	@GetMapping("/courseNameMatching")
	public ResponseEntity<List<CourseResponseDto>> getCourseByNameContains(@RequestParam String courseName){
		List<CourseResponseDto> allCourses = courseService.getCoursesByName(courseName);
		return new ResponseEntity<>(allCourses, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteByCourseCode")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteCourse(@RequestParam String courseCode) {
		courseService.deleteCourseByCourseCode(courseCode);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
	}
	
	@GetMapping("/findByCourseCode")
	public ResponseEntity<CourseResponseDto> findCourseByCourseCode(@RequestParam String courseCode){
		CourseResponseDto course = courseService.findCourseByCourseCode(courseCode);
		return new ResponseEntity<>(course,HttpStatus.OK);
	}
	
	@PutMapping("/updateCourse")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CourseResponseDto> updateCourseByCourseCode(@RequestParam String courseCode,@RequestBody CourseRequestDto courseRequestDto ){
		CourseResponseDto updatedCourse = courseService.updateCourseByCourseCode(courseCode, courseRequestDto);
		return new ResponseEntity<>(updatedCourse,HttpStatus.OK);
	}
	@PostMapping("/saveMultipleCourses")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<CourseResponseDto>> addMultipleCourses(@RequestBody List<CourseRequestDto> courseRequestDtoList){
		List<CourseResponseDto> savedCourses = courseService.addMultipleCourses(courseRequestDtoList);
		return new ResponseEntity<>(savedCourses,HttpStatus.CREATED);	
	}
}
