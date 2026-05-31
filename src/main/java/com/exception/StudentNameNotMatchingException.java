package com.exception;

public class StudentNameNotMatchingException extends RuntimeException {

	public StudentNameNotMatchingException(String message) {
		super(message);
	}
}
