package com.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.EnrollmentRequestDTO;
import com.dto.EnrollmentResponseDTO;
import com.exception.CourseIdNotFoundException;
import com.exception.DuplicateEnrollmentException;
import com.exception.NoEnrollmentsFoundException;
import com.exception.StudentIdNotFoundException;
import com.model.Course;
import com.model.Enrollment;
import com.model.Student;
import com.repository.CourseRepository;
import com.repository.EnrollmentRepository;
import com.repository.StudentRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{

	private EnrollmentRepository enrollmentRepository;
	
	public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
		this.enrollmentRepository = enrollmentRepository;
	}
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	

	@Override
	public EnrollmentResponseDTO enrollCourse(EnrollmentRequestDTO enrollmentRequestDTO) {
		
		long studentId = enrollmentRequestDTO.getStudentId();
		long courseId = enrollmentRequestDTO.getCourseId();
		
		Student student = studentRepository.findById(studentId)
							.orElseThrow(() ->  new StudentIdNotFoundException("Student Id is NOT FOUND for the Id : "+studentId));
		
		Course course = courseRepository.findById(courseId)
						.orElseThrow(() -> new CourseIdNotFoundException("Course Id is NOT FOUND for the Id : " +courseId));
		
		Optional<Enrollment> duplicateEnrollment = enrollmentRepository.findByStudent_StudentIdAndCourse_CourseId(studentId, courseId);
		
		if(duplicateEnrollment.isPresent()) {
			throw new DuplicateEnrollmentException("The Student has already enrolled this course");
		}
			
		Enrollment enrollment = new Enrollment();
		enrollment.setCourse(course);
		enrollment.setStudent(student);
		enrollment.setStatus("ACTIVE");
		enrollment.setEnrollmentDate(LocalDate.now());
		
		Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
		
		EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
		
		enrollmentResponseDTO.setEnrollmentId(savedEnrollment.getEnrollmentId());
		enrollmentResponseDTO.setStudentName(savedEnrollment.getStudent().getUserName());
		enrollmentResponseDTO.setCourseName(savedEnrollment.getCourse().getCourseName());
	    enrollmentResponseDTO.setEnrollmentDate(savedEnrollment.getEnrollmentDate());
	    enrollmentResponseDTO.setStatus(savedEnrollment.getStatus());
							
		return enrollmentResponseDTO;
	}

	@Override
	public List<EnrollmentResponseDTO> getAllEnrollments() {
		
		List<Enrollment> allEnrollments = enrollmentRepository.findAll();
		if(allEnrollments.isEmpty()) {
			throw new NoEnrollmentsFoundException("There Are No Enrollments Found..!");
		}
		List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentResponseDTOListBuilder(allEnrollments);
		
		return enrollmentResponseDTOList;
	}
	
	@Override
	public EnrollmentResponseDTO getEnrollmentById(long enrollmentId) {
		 Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
										 .orElseThrow(() -> new NoEnrollmentsFoundException("There is No Enrollments Found with Id : "+enrollmentId));
		EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
		
		enrollmentResponseDTO.setEnrollmentId(enrollment.getEnrollmentId());
		enrollmentResponseDTO.setStudentName(enrollment.getStudent().getUserName());
		enrollmentResponseDTO.setCourseName(enrollment.getCourse().getCourseName());
		enrollmentResponseDTO.setEnrollmentDate(enrollment.getEnrollmentDate());
		enrollmentResponseDTO.setStatus(enrollment.getStatus());
		return enrollmentResponseDTO;
	}

	@Override
	public List<EnrollmentResponseDTO> getEnrollmentsByCourseName(String courseName) {
		List<Enrollment> enrollmentList = enrollmentRepository.findByCourse_CourseNameContains(courseName);
		
		if(enrollmentList.isEmpty()) {
			throw new NoEnrollmentsFoundException("There Are No Enrollments Found..!");
		}
		List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentResponseDTOListBuilder(enrollmentList);
		
		return enrollmentResponseDTOList;
	}
	
	@Override
	public void deleteEnrollmentById(long enrollmentId) {
		 Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
		 					 .orElseThrow(() -> new NoEnrollmentsFoundException("There is No Enrollments Found with Id : "+enrollmentId));
		 enrollmentRepository.delete(enrollment);
	}
	
	@Override
	public List<EnrollmentResponseDTO> getEnrollmentsByStudentName(String studentName) {
		List<Enrollment> enrollmentList = enrollmentRepository.findByStudent_UserNameContains(studentName);
		
		if(enrollmentList.isEmpty()) {
			throw new NoEnrollmentsFoundException("There Are No Enrollments Found..!");
		}
		List<EnrollmentResponseDTO> enrollmentResponseDTOList = enrollmentResponseDTOListBuilder(enrollmentList);
		
		return enrollmentResponseDTOList;
	}
	
	//HelperMethod
	public List<EnrollmentResponseDTO> enrollmentResponseDTOListBuilder(List<Enrollment> list) {
	
		List<EnrollmentResponseDTO> enrollmentResponseDTOList = new ArrayList<>();
	
			for(Enrollment enrol : list) {
				EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
				enrollmentResponseDTO.setEnrollmentId(enrol.getEnrollmentId());
				enrollmentResponseDTO.setStudentName(enrol.getStudent().getUserName());
				enrollmentResponseDTO.setCourseName(enrol.getCourse().getCourseName());
				enrollmentResponseDTO.setEnrollmentDate(enrol.getEnrollmentDate());
				enrollmentResponseDTO.setStatus(enrol.getStatus());
				enrollmentResponseDTOList.add(enrollmentResponseDTO);
			}
	return enrollmentResponseDTOList;
	}

	

	
}
