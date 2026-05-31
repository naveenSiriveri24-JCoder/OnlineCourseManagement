package com.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dto.StudentRequestDTO;
import com.dto.StudentResponseDTO;
import com.exception.EmailAlreadyExistsException;
import com.exception.StudentIdNotFoundException;
import com.exception.StudentNameNotMatchingException;
import com.model.Student;
import com.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

	private final StudentRepository studentRepository;
	
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	public StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO) {
		
		Student st = new Student();
		st.setUserName(studentRequestDTO.getUserName());
		st.setPassword(studentRequestDTO.getPassword());
		st.setEmail(studentRequestDTO.getEmail());
		st.setPhone(studentRequestDTO.getPhone());
		st.setAge(studentRequestDTO.getAge());
		st.setAuthorities(studentRequestDTO.getAuthorities());
		
		Student savedStudent = studentRepository.save(st);
		
		String studentCode = "STDN-" + String.format("%05d",savedStudent.getStudentId() );
		savedStudent.setStudentCode(studentCode);
		
		studentRepository.save(savedStudent);
		
		StudentResponseDTO stResp = new StudentResponseDTO();
		BeanUtils.copyProperties(savedStudent, stResp);
		
		return stResp;
	}

	@Override
	public List<StudentResponseDTO> saveMultipleStudents(List<StudentRequestDTO> studentRequestDTOList) {
		
		List<Student> stList = new ArrayList<>();
		
		for(StudentRequestDTO stReqList : studentRequestDTOList) {
			Student st = new Student();
			st.setUserName(stReqList.getUserName());
			st.setEmail(stReqList.getEmail());
			st.setPhone(stReqList.getPhone());
			st.setAge(stReqList.getAge());
			stList.add(st);
		}
		
		List<Student> saveAll = studentRepository.saveAll(stList);
		
		List<StudentResponseDTO> stRespList = new ArrayList<>();
		
		for(Student stResp : saveAll ) {
			StudentResponseDTO stRespObj = new StudentResponseDTO();
			stRespObj.setStudentId(stResp.getStudentId());
			stRespObj.setUserName(stResp.getUserName());
			stRespObj.setEmail(stResp.getEmail());
			stRespObj.setPhone(stResp.getPhone());
			stRespObj.setAge(stResp.getAge());
			stRespList.add(stRespObj);
		}
		return stRespList;
	}

	@Override
	public StudentResponseDTO getStudentById(long studentId) {
		
		Student st = studentRepository.findById(studentId)
						 .orElseThrow(() -> new StudentIdNotFoundException("No Student Found with studentId : "+studentId));
		StudentResponseDTO stResp = new StudentResponseDTO();
		BeanUtils.copyProperties(st, stResp);
		return stResp ;
	}

	@Override
	public List<StudentResponseDTO> getAllStudents() {
		List<Student> allStudents = studentRepository.findAll();
		
		List<StudentResponseDTO> getAllStudents = studentResponseDTOBuilder(allStudents);
		
		return getAllStudents;
	}

	@Override
	public StudentResponseDTO updateStudent(long studentId, StudentRequestDTO studentRequestDTO) {
		
		Student st = studentRepository.findById(studentId)
				 .orElseThrow(() -> new StudentIdNotFoundException("No Student Found with studentId : "+studentId));
		
		if (studentRepository.existsByEmailAndStudentIdNot(studentRequestDTO.getEmail(), studentId)) {
	        throw new EmailAlreadyExistsException("Email already exists");
	    }
		
		// Update existing entity
		st.setUserName(studentRequestDTO.getUserName());
		st.setEmail(studentRequestDTO.getEmail());
		st.setPhone(studentRequestDTO.getPhone());
		st.setAge(studentRequestDTO.getAge());
		
		Student updatedStudent = studentRepository.save(st);
		
		StudentResponseDTO stResp = new StudentResponseDTO();
		BeanUtils.copyProperties(updatedStudent, stResp);
		return stResp;
		
	}
	
	@Override
	public void deleteStudentById(long studentId) {
		Student student = studentRepository.findById(studentId)
				 .orElseThrow(() -> new StudentIdNotFoundException("No Student Found with studentId : "+studentId));
		studentRepository.delete(student);	
	}
	
	@Override
	public List<StudentResponseDTO> findStudentByNameMatches(String studentName)  {
		
		List<Student> studentList = studentRepository.findByUserNameContains(studentName);
		if(studentList.isEmpty()) {
			throw new StudentNameNotMatchingException("No Student Found with : " +studentName);
		}
		
		List<StudentResponseDTO> studentResponseDTOList = studentResponseDTOBuilder(studentList);
		
		return studentResponseDTOList;
	}
	
	
	
	//HelperMethod
	public List<StudentResponseDTO> studentResponseDTOBuilder(List<Student> allStudents){
		
		List<StudentResponseDTO> stRespList = new ArrayList<>();
		
		for(Student stResp : allStudents) {
			StudentResponseDTO stRespObj = new StudentResponseDTO();
			stRespObj.setStudentId(stResp.getStudentId());
			stRespObj.setStudentCode(stResp.getStudentCode());
			stRespObj.setUserName(stResp.getUserName());
			stRespObj.setEmail(stResp.getEmail());
			stRespObj.setPhone(stResp.getPhone());
			stRespObj.setAge(stResp.getAge());
			stRespList.add(stRespObj);
		}
		return stRespList;
	}

	

	
}
