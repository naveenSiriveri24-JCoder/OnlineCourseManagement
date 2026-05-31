package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.EnrollmentRequestDTO;
import com.dto.EnrollmentResponseDTO;

@Service
public interface EnrollmentService {

	EnrollmentResponseDTO enrollCourse(EnrollmentRequestDTO enrollmentRequestDTO);

	List<EnrollmentResponseDTO> getAllEnrollments();

	EnrollmentResponseDTO getEnrollmentById(long enrollmentId);

	List<EnrollmentResponseDTO> getEnrollmentsByCourseName(String courseName);

	void deleteEnrollmentById(long enrollmentId);

	List<EnrollmentResponseDTO> getEnrollmentsByStudentName(String studentName);

}
