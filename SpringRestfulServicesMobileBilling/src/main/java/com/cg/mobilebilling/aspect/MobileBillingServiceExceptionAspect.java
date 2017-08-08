package com.cg.mobilebilling.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.mobilebilling.customresponse.CustomResponse;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;

@ControllerAdvice(basePackages={"com.cg.mobilebilling.controllers"})
public class MobileBillingServiceExceptionAspect {

	@ExceptionHandler(CustomerDetailsNotFoundException.class)
	public ResponseEntity<CustomResponse> handelCustomerDetailsNotFoundException(Exception e){
		CustomResponse response = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
}

