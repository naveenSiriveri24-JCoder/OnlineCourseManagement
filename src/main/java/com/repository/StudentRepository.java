package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

//	Check email exists
//	BUT ignore the current user id
	boolean existsByEmailAndStudentIdNot(String email, long studentId); // refer updateStudent() in StudentServiceImpl class
	
	List<Student> findByUserNameContains(String studentName);
	
	Student findByUserName(String username);
}
