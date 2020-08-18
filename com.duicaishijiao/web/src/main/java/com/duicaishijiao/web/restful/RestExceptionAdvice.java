package com.duicaishijiao.web.restful;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionAdvice{
	
	
	@ExceptionHandler
	public ResponseEntity<Object> exceptionHandle(Exception e , HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<Object>(e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
