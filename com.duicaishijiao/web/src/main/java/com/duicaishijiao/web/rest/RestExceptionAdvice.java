package com.duicaishijiao.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.duicaishijiao.web.Exception.BusinessException;
import com.duicaishijiao.web.Exception.NotFoundException;

@RestControllerAdvice
public class RestExceptionAdvice{
	
	private static final Logger log = LogManager.getLogger();
	
	
	@ExceptionHandler
	public ResponseEntity<Object> exceptionHandle(Exception e , HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		if(e instanceof NotFoundException) {
			return new ResponseEntity<Object>(e.getMessage(), headers, HttpStatus.NOT_FOUND);
		}
		if(e instanceof BusinessException) {
			return new ResponseEntity<Object>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
		}
		e.printStackTrace();
		log.error("rest api 接口异常：",e);
		return new ResponseEntity<Object>(e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
