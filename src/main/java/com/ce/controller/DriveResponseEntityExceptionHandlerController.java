package com.ce.controller;

import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ce.entity.APIResponse;
import com.ce.exception.DriveException;
import com.ce.exception.ErrorCodes;
/**
 * 
 * @author DKP
 * Handle All Exception using AOP
 *
 */
@ControllerAdvice
public class DriveResponseEntityExceptionHandlerController extends ResponseEntityExceptionHandler {
	@Autowired
	MessageSource messageSource;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuffer errors = new StringBuffer();
		APIResponse apiResponse = null;
		for (FieldError errs : ex.getBindingResult().getFieldErrors()) {
			errors.append(errs.getField() + ":" + errs.getDefaultMessage()+",");
		}
		apiResponse = new APIResponse("Field Validation",ErrorCodes.FIELD_NOT_VALID,errors.toString() );
	
		return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
	}
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
	  NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
	 
		APIResponse apiResponse = new APIResponse(ErrorCodes.NO_HANDLER_FOUND, error+ex.getLocalizedMessage());
	    return new ResponseEntity<Object>(apiResponse,HttpStatus.EXPECTATION_FAILED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(DriveException.class)
	public ResponseEntity<Object> mgException(DriveException ex, WebRequest request, Locale locale) {
		logger.error("Error Code: " + ex.getErrorCode() + "Error Message: " + ex.getErrorMessage());
		APIResponse apiResponse = null;
		if (ex.getErrorMessage() != null && !ex.getErrorMessage().isEmpty()) {
			apiResponse = new APIResponse(ex.getStatus(),ex.getErrorCode(), ex.getErrorMessage().toString());
		} else {
			apiResponse = new APIResponse(ex.getStatus(),ex.getErrorCode(),
					messageSource.getMessage(ex.getErrorCode(), new Object[] {}, locale));
		}
		return new ResponseEntity(apiResponse, HttpStatus.EXPECTATION_FAILED);
	}

}
