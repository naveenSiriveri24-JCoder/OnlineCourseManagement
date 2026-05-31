package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByCourseNameContains(String courseName);
	
	Optional<Course> findByCourseCode(String courseCode);
	
}
