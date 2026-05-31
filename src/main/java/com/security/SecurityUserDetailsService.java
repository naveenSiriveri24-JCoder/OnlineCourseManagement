package com.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.Authority;
import com.model.Student;
import com.repository.StudentRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Student student = studentRepository.findByUserName(username);
		
		if (student == null) {
	        throw new UsernameNotFoundException("Student not found with name: " + username);
	    }
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		List<Authority> authoritiesFromDB = student.getAuthorities();
		
		for(Authority authority : authoritiesFromDB ) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+authority.getRole()));
		}
		
		return User
				.builder()
				.username(student.getUserName())
				.password(student.getPassword())
				.authorities(authorities)
				.build();
	}

}
