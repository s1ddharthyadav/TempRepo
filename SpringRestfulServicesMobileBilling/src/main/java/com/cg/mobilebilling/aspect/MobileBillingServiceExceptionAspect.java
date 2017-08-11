package com.cg.mobilebilling.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.cg.mobilebilling.customresponse.CustomResponse;
import com.cg.mobilebilling.exceptions.BillDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.InvalidBillMonthException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;

@ControllerAdvice(basePackages={"com.cg.mobilebilling.controllers"})
public class MobileBillingServiceExceptionAspect {

	@ExceptionHandler(CustomerDetailsNotFoundException.class)
	public ResponseEntity<CustomResponse> handelCustomerDetailsNotFoundException(Exception e){
		CustomResponse response = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(BillDetailsNotFoundException.class)
	public ResponseEntity<CustomResponse> handelBillDetailsNotFoundException(Exception e){
		CustomResponse response = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(BillingServicesDownException.class)
	public ResponseEntity<CustomResponse> handelBillingServicesDownException(Exception e){
		CustomResponse response = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(InvalidBillMonthException.class)
	public ResponseEntity<CustomResponse> handelInvalidBillMonthException(Exception e){
		CustomResponse response = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(PlanDetailsNotFoundException.class)
	public ResponseEntity<CustomResponse> handelPlanDetailsNotFoundException(Exception e){
		CustomResponse response = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(PostpaidAccountNotFoundException.class)
	public ResponseEntity<CustomResponse> handelPostpaidAccountNotFoundException(Exception e){
		CustomResponse response = new CustomResponse(HttpStatus.EXPECTATION_FAILED.value(),e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
	}
}