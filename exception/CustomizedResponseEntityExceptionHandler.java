package com.javasampleapproach.mysql.exception;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javasampleapproach.mysql.model.EntityAlreadyExistsException;
import com.javasampleapproach.mysql.model.EntityNotFoundException;


@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	       String error = "Malformed JSON request";
	       return buildResponseEntity(new ErrorDetails(HttpStatus.BAD_REQUEST, error, ex));
	   }

	   private ResponseEntity<Object> buildResponseEntity(ErrorDetails ErrorDetails) {
	       return new ResponseEntity<>(ErrorDetails, ErrorDetails.getStatus());
	   }
	   
	   
	   @ExceptionHandler(EntityNotFoundException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
	       String message = "Entity Not Found";
	       ErrorDetails ErrorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, message, ex);
	       
	       ErrorDetails.setDebugMessage(ex.getMessage());
	       
	       return buildResponseEntity(ErrorDetails);
	   }
	   
	   @ExceptionHandler(EntityAlreadyExistsException.class)
	   protected ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex)
	   {
		   String message = "Entity Already Exists";
		   ErrorDetails ErrorDetails = new ErrorDetails(HttpStatus.OK, message, ex);
		   ErrorDetails.setDebugMessage(ex.getMessage());
		   ErrorDetails.setDetails(ex.toString());
		   return buildResponseEntity(ErrorDetails);
		   
	   }
	   
	   @Override
	   protected ResponseEntity<Object> handleMethodArgumentNotValid(
	            MethodArgumentNotValidException ex,
	            HttpHeaders headers,
	            HttpStatus status,
	            WebRequest request) {
		   ErrorDetails ErrorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST);
		   ErrorDetails.setMessage("Validation error");
		   ErrorDetails.addValidationErrors(ex.getBindingResult().getFieldErrors());
		   ErrorDetails.addValidationError(ex.getBindingResult().getGlobalErrors());
	        return buildResponseEntity(ErrorDetails);
	    }
	   
	   
	   @ExceptionHandler(javax.validation.ConstraintViolationException.class)
	    protected ResponseEntity<Object> handleConstraintViolation(
	            javax.validation.ConstraintViolationException ex) {
		   ErrorDetails ErrorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST);
		   ErrorDetails.setMessage("Validation error");
		   ErrorDetails.addValidationErrors(ex.getConstraintViolations());
	        return buildResponseEntity(ErrorDetails);
	    }

}
