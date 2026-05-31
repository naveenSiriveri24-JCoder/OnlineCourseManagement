package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

	Optional<Enrollment> findByStudent_StudentIdAndCourse_CourseId(long studentId, long courseId); // the method name is like this because,
	// the studentId, courseId are not from the Enrollment model class.
	
	List<Enrollment> findByCourse_CourseNameContains(String courseName);
	
	List<Enrollment> findByStudent_UserNameContains(String studentName);
}
