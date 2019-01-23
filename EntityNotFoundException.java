package com.javasampleapproach.mysql.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	  public EntityNotFoundException(String exception) {
	    super(exception);
	  }

	}