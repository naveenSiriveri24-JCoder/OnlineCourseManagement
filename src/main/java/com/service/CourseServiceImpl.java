package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dto.CourseRequestDto;
import com.dto.CourseResponseDto;
import com.exception.CourseNotFoundException;
import com.model.Course;
import com.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService  {

	private CourseRepository courseRepository;
	
	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	@Override
	public void addCourse(CourseRequestDto courseRequestDto) {
		
		Course course = new Course();
		course.setCourseName(courseRequestDto.getCourseName());
		course.setInstructor(courseRequestDto.getInstructor());
		course.setPrice(courseRequestDto.getPrice());
		course.setDuration(courseRequestDto.getDuration());
		// First save to generate numeric ID
		Course savedCourse = courseRepository.save(course);	
        // Generate formatted course code
        String courseCode = "CRS-" + String.format("%05d", savedCourse.getCourseId());
        savedCourse.setCourseCode(courseCode);

        // Save again with course code
        Course updatedCourse = courseRepository.save(savedCourse);
	}
	
	@Override
	public List<CourseResponseDto> getAllCourses() {
		
		List<Course> allCourses = courseRepository.findAll();
		
		if(allCourses.isEmpty()) {
			throw new CourseNotFoundException("No course found : The List is Empty ");
		}
		List<CourseResponseDto> courseResponseDtoList = courseResponseDtoListBuilder(allCourses);
		
		return courseResponseDtoList;
	}
	
	@Override
	public List<CourseResponseDto> getCoursesByName(String courseName) {
		
		List<Course> courseNameContainsList = courseRepository.findByCourseNameContains(courseName);
		
		if(courseNameContainsList.isEmpty()) {
	        throw new CourseNotFoundException("No course found containing name : " + courseName);
	    }

		List<CourseResponseDto> courseResponseDtoList = courseResponseDtoListBuilder(courseNameContainsList);
		
		return courseResponseDtoList;
	}
	
	@Override
	public void deleteCourseByCourseCode(String courseCode) {
		
		 Course course = courseRepository.findByCourseCode(courseCode)
				 		.orElseThrow(() -> new CourseNotFoundException("No course found with code : " + courseCode));
		courseRepository.delete(course);
	}

	@Override
	public CourseResponseDto findCourseByCourseCode(String courseCode) {
		
		Course course = courseRepository.findByCourseCode(courseCode)
						.orElseThrow(() -> new CourseNotFoundException("No course found with code : " + courseCode));
		CourseResponseDto courseResponseDto = new CourseResponseDto();
		BeanUtils.copyProperties(course, courseResponseDto);
		return courseResponseDto;
	}
	
	@Override
	public CourseResponseDto updateCourseByCourseCode(String courseCode, CourseRequestDto courseRequestDto) {
		Course course = courseRepository.findByCourseCode(courseCode)
				.orElseThrow(() -> new CourseNotFoundException("No course found with code : " + courseCode));
		
		BeanUtils.copyProperties(courseRequestDto, course);
		courseRepository.save(course);
		
		CourseResponseDto courseResponseDto = new CourseResponseDto();
		BeanUtils.copyProperties(course, courseResponseDto);
		return courseResponseDto;
	}
	
	@Override
	public List<CourseResponseDto> addMultipleCourses(List<CourseRequestDto> courseRequestDtoList) {
		
		List<Course> courseList = new ArrayList<>();
		
		for(CourseRequestDto crs: courseRequestDtoList) {
			Course course = new Course();
			course.setCourseName(crs.getCourseName());
			course.setInstructor(crs.getInstructor());
			course.setPrice(crs.getPrice());
			course.setDuration(crs.getDuration());
			courseList.add(course);
		}
			
		List<Course> savedCourseList = courseRepository.saveAll(courseList);
		
			for(Course savedCourse : savedCourseList) {
				
			String courseCode = "CRS-" + String.format("%05d", savedCourse.getCourseId());
	        savedCourse.setCourseCode(courseCode);
	        
			}
			List<Course> updatedCourseList = courseRepository.saveAll(savedCourseList);
		
		List<CourseResponseDto> courseResponseDtoList = courseResponseDtoListBuilder(updatedCourseList);
		return courseResponseDtoList;
	}
	
	
	//HELPER METHOD:
	public List<CourseResponseDto> courseResponseDtoListBuilder(List<Course> allCourses){
	
		List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
	
		for(Course crs : allCourses ) {
			CourseResponseDto course = new CourseResponseDto();
			course.setCourseId(crs.getCourseId());
			course.setCourseCode(crs.getCourseCode());
			course.setCourseName(crs.getCourseName());
			course.setInstructor(crs.getInstructor());
			course.setPrice(crs.getPrice());
			course.setDuration(crs.getDuration());
			
			courseResponseDtoList.add(course);
		}
		return courseResponseDtoList;
	}
	
	
	
	
}
