package com.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(StudentIdNotFoundException.class)
	public ResponseEntity<String> handleStudentIdNotFoundException(StudentIdNotFoundException ex) {
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleCourseNotFoundException(CourseNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleStudentNameNotMatchingException(StudentNameNotMatchingException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleCourseIdNotFoundException(CourseIdNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	//More professional way to handle exception (returning the complete json)
	@ExceptionHandler(DuplicateEnrollmentException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEnrollmentException(DuplicateEnrollmentException ex, HttpServletRequest request){
		
		ErrorResponse error = new ErrorResponse(
									                LocalDateTime.now(),
									                HttpStatus.CONFLICT.value(),
									                "Duplicate Enrollment",
									                ex.getMessage(),
									                request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);	
	}
	
	@ExceptionHandler(NoEnrollmentsFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoEnrollmentsFoundException(NoEnrollmentsFoundException ex, HttpServletRequest request){
		ErrorResponse error = new ErrorResponse(
									                LocalDateTime.now(),
									                HttpStatus.NOT_FOUND.value(),
									                "Enrollments Not Found",
									                ex.getMessage(),
									                request.getRequestURI());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);	
	}
}
