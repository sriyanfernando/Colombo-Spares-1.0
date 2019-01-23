package com.javasampleapproach.mysql.model;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.OK)
public class EntityAlreadyExistsException extends RuntimeException {

	  public EntityAlreadyExistsException(String exception) {
	    super(exception);
	  }

	}