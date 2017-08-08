package com.cg.mobilebilling.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.services.BillingServices;

@RestController
public class MobileBillingController {

	@Autowired
	private BillingServices services;
	
	
	public MobileBillingController(){
		System.out.println("Mobile Billing Controller");
	}

	
	
	@RequestMapping(value="/acceptCustomerDetail",method=RequestMethod.POST,consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String>acceptProductDetail(@ModelAttribute Customer customer) throws BillingServicesDownException{
		services.acceptCustomerDetails(customer);
		return new ResponseEntity<>("Customer details succesfully added",HttpStatus.OK);
		
	}
	
	@RequestMapping(value={"/CustomerDetailsRequestParam"},headers="Accept=application/json")
	public ResponseEntity<Customer>getCustomerDetails(@RequestParam("customerID")int customerID) throws CustomerDetailsNotFoundException, BillingServicesDownException{
		Customer customer=services.getCustomerDetails(customerID);
		System.out.println("details "+customer);
		
		return new ResponseEntity<>(customer,HttpStatus.OK);
	
	}
	@RequestMapping(value={"/allCustomerDetailsJSON"},headers="Accept=application/json")
	public ResponseEntity<ArrayList<Customer>> getAllCustomerDetailsJSON() throws CustomerDetailsNotFoundException, BillingServicesDownException{
	ArrayList<Customer> customerList=(ArrayList<Customer>) services.getAllCustomerDetails();
	return new ResponseEntity<>(customerList,HttpStatus.OK);
	}

	@RequestMapping(value="/deleteCustomerDetail/{customerID}",method=RequestMethod.DELETE)
	public ResponseEntity<String>deleteCustomerDetail(@PathVariable("customerID")int customerID)throws CustomerDetailsNotFoundException, BillingServicesDownException{
		boolean customer = services.deleteCustomer(customerID);
			if(customer==false)throw new CustomerDetailsNotFoundException("Customer detail not found with product code"+customerID);
		return new ResponseEntity<>("Customer details succesfully deleted",HttpStatus.OK);
		
	}
}