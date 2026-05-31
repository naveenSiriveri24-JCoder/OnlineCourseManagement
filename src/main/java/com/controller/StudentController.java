package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.StudentRequestDTO;
import com.dto.StudentResponseDTO;
import com.model.Student;
import com.security.JWTService;
import com.service.StudentService;

@RestController
@RequestMapping("/st")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/sign-up")
	public StudentResponseDTO saveStudent(@RequestBody StudentRequestDTO studentRequestDTO) {
		
		String encodedpassword = passwordEncoder.encode(studentRequestDTO.getPassword());
		studentRequestDTO.setPassword(encodedpassword);
		
		StudentResponseDTO stResp = studentService.saveStudent(studentRequestDTO);
		return stResp;
	}
	
	@PostMapping("/save-all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<StudentResponseDTO> saveMultipleStudents(@RequestBody List<StudentRequestDTO> studentRequestDTOList){
		List<StudentResponseDTO> stRespList = studentService.saveMultipleStudents(studentRequestDTOList);
		return stRespList;	
	}
	
	@GetMapping("/get/{studentId}")
	public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable long studentId) {
		StudentResponseDTO studentById = studentService.getStudentById(studentId);
		return new ResponseEntity<>(studentById, HttpStatus.OK);	
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<List<StudentResponseDTO>> getAllStudents(){
		List<StudentResponseDTO> allStudents = studentService.getAllStudents();
		return new ResponseEntity<>(allStudents, HttpStatus.OK);	
	}
	
	@PutMapping("update/{studentId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable long studentId, @RequestBody StudentRequestDTO studentRequestDTO ) {
		StudentResponseDTO updatedStudent = studentService.updateStudent(studentId,studentRequestDTO);
		return new ResponseEntity<>(updatedStudent, HttpStatus.OK);	
	}
	
	@DeleteMapping("/delete/{studentId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteStudent(@PathVariable long studentId){
		studentService.deleteStudentById(studentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@GetMapping("/findByStudentNameContains")
	public ResponseEntity<List<StudentResponseDTO>> findStudentByNameMatches(@RequestParam String studentName){
		List<StudentResponseDTO> studentByNameMatchesList = studentService.findStudentByNameMatches(studentName);
		return new ResponseEntity<>(studentByNameMatchesList,HttpStatus.OK);
		
	}
	
	//Login
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private  JWTService JWTService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Student student) {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(student.getUserName(), student.getPassword()));
		}catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalied Credentials");
		}
		String jwtToken = JWTService.generateToken(student.getUserName());
		
		return ResponseEntity.ok(jwtToken);
	}
}
