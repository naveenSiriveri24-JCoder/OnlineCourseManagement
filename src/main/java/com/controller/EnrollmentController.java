package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.EnrollmentRequestDTO;
import com.dto.EnrollmentResponseDTO;
import com.service.EnrollmentService;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

	private EnrollmentService enrollmentService;

	public EnrollmentController(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}
	
	@PostMapping("/enroll")
	public ResponseEntity<EnrollmentResponseDTO> enrollCourse(@RequestBody EnrollmentRequestDTO enrollmentRequestDTO){
		EnrollmentResponseDTO enrolledCourse = enrollmentService.enrollCourse(enrollmentRequestDTO);
		return new ResponseEntity<>(enrolledCourse,HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllEnrollments")
	public ResponseEntity<List<EnrollmentResponseDTO>> getAllEnrollments(){
		List<EnrollmentResponseDTO> allEnrollments = enrollmentService.getAllEnrollments();
		return new ResponseEntity<>(allEnrollments, HttpStatus.OK);
	}
	
	@GetMapping("/getById/{enrollmentId}")
	public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(@PathVariable long enrollmentId){
		EnrollmentResponseDTO enrollmentById = enrollmentService.getEnrollmentById(enrollmentId);
		return new ResponseEntity<>(enrollmentById, HttpStatus.OK);
	}
	
	@GetMapping("/getByCourseNameContains")
	public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByCourseName(@RequestParam String courseName){
		List<EnrollmentResponseDTO> enrollmentsByCourseName = enrollmentService.getEnrollmentsByCourseName(courseName);
		return new ResponseEntity<>(enrollmentsByCourseName, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{enrollmentId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteEnrollmentById(@PathVariable long enrollmentId){
		enrollmentService.deleteEnrollmentById(enrollmentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/enrollmentsByStudentName/{studentName}")
	public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByStudentName(@PathVariable String studentName){
		List<EnrollmentResponseDTO> enrollmentList = enrollmentService.getEnrollmentsByStudentName(studentName);
		return ResponseEntity.ok(enrollmentList);
	}
}
