package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.StudentRequestDTO;
import com.dto.StudentResponseDTO;


@Service
public interface StudentService {

	public StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO);

	public List<StudentResponseDTO> saveMultipleStudents(List<StudentRequestDTO> studentRequestDTOList);

	public StudentResponseDTO getStudentById(long studentId);

	public List<StudentResponseDTO> getAllStudents();

	public StudentResponseDTO updateStudent(long studentId, StudentRequestDTO studentRequestDTO);

	public void deleteStudentById(long studentId);

	public List<StudentResponseDTO> findStudentByNameMatches(String studentName);
	
}
