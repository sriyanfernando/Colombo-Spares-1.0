package com.javasampleapproach.mysql.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import groovy.transform.EqualsAndHashCode;

public class ErrorDetails {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	 private LocalDateTime timestamp;
	 
	  private String message;
	  private String details;
	  private HttpStatus status;
	  private String debugMessage;
	  private List<SubErrorDetails> subErrors;
	  

	  public ErrorDetails(HttpStatus status, String message, Throwable ex) {
	    super();
	    this.timestamp = LocalDateTime.now();
	    this.setStatus(status);
	    this.message = message;
	    this.details = ex.toString();
	    this.debugMessage = ex.getLocalizedMessage();
	  }
	  
	  public ErrorDetails(HttpStatus status){
		  super();
		  this.status=status;
		  this.message="Unexpected error";
		  
	  }
	  
	  public ErrorDetails(HttpStatus status,Throwable ex) {
		  super();
		  this.status=status;
		  this.debugMessage = ex.getLocalizedMessage();
	  }
	  
	  
	  private void addSubError(SubErrorDetails subError) {
	        if (subErrors == null) {
	            subErrors = new ArrayList<>();
	        }
	        subErrors.add(subError);
	    }
	  
	  private void addValidationError(String object, String field, Object rejectedValue, String message) {
	        addSubError(new ApiValidationError(object, field, rejectedValue, message));
	    }
	  
	  private void addValidationError(String object, String message) {
	        addSubError(new ApiValidationError(object, message));
	    }

	    private void addValidationError(FieldError fieldError) {
	        this.addValidationError(
	                fieldError.getObjectName(),
	                fieldError.getField(),
	                fieldError.getRejectedValue(),
	                fieldError.getDefaultMessage());
	    }
	    
	    void addValidationErrors(List<FieldError> fieldErrors) {
	        fieldErrors.forEach(this::addValidationError);
	    }

	    private void addValidationError(ObjectError objectError) {
	        this.addValidationError(
	                objectError.getObjectName(),
	                objectError.getDefaultMessage());
	    }

	    void addValidationError(List<ObjectError> globalErrors) {
	        globalErrors.forEach(this::addValidationError);
	    }
	    
	    private void addValidationError(ConstraintViolation<?> cv) {
	        this.addValidationError(
	                cv.getRootBeanClass().getSimpleName(),
	                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
	                cv.getInvalidValue(),
	                cv.getMessage());
	    }
	    
	    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
	        constraintViolations.forEach(this::addValidationError);
	    }
	  
	 
	  
	  
	  public LocalDateTime getTimestamp() {
	    return timestamp;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public String getDetails() {
	    return details;
	  }
	  
	  public void setDetails(String details) {
		  this.details = details;
	  }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public void setMessage(String message) {
		this.message = message;
		
	}
	
	
	abstract class SubErrorDetails {

    }
	
	@EqualsAndHashCode(callSuper = false)
	class  ApiValidationError extends SubErrorDetails{
		
		private String object;
		private String field;
		private Object rejectedValue;
		private String message;
		
		ApiValidationError(String object,String field, Object rejectedValue, String message) {
		       this.object = object;
		       this.field = field;
		       this.rejectedValue = rejectedValue;
		       this.message = message;
		   }
		
		ApiValidationError(String object,String message) {
		       this.object = object;
		       this.message = message;
		   }
		
		
		
		
		public String getObject() {
			return object;
		}
		public void setObject(String object) {
			this.object = object;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public Object getRejectedValue() {
			return rejectedValue;
		}
		public void setRejectedValue(Object rejectedValue) {
			this.rejectedValue = rejectedValue;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}

	

	}
}

class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(Object value) {
        return value.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}




