package com.cg.mobilebilling.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.beans.StandardPlan;
import com.cg.mobilebilling.exceptions.BillDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.InvalidBillMonthException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;
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

	@RequestMapping(value="/insertPlanDetail",method=RequestMethod.POST,consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String>insertPlanDetail(@ModelAttribute StandardPlan plan) throws PlanDetailsNotFoundException{
		services.insertPlan(plan);
		return new ResponseEntity<>("Plan details succesfully added",HttpStatus.OK);
	}

	@RequestMapping(value="/insertPostpaidPlan",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public ResponseEntity<String>insertPostpaidPlanDetail(@RequestParam("customerID")int customerID, @RequestParam("planID") int planID) throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException{
		services.openPostpaidMobileAccount(customerID, planID);
		return new ResponseEntity<>("Postpaid account details succesfully added",HttpStatus.OK);
	}

	@RequestMapping(value="/generateBill", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<String>generateBill(@RequestParam("customerID") int customerID, @RequestParam("mobileNo") long mobileNo, @RequestParam("billMonth") String billMonth, @RequestParam("noOfLocalSMS") int noOfLocalSMS, @RequestParam("noOfStdSMS") int noOfStdSMS, @RequestParam("noOfLocalCalls") int noOfLocalCalls, @RequestParam("noOfStdCalls") int noOfStdCalls, @RequestParam("internetDataUsageUnits") int internetDataUsageUnits ) throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillingServicesDownException, PlanDetailsNotFoundException{
		Bill generatedBill= services.generateMonthlyMobileBill(customerID, mobileNo, billMonth, noOfLocalSMS, noOfStdSMS, noOfLocalCalls, noOfStdCalls, internetDataUsageUnits);
		return new ResponseEntity<>("Bill for mobile no. "+mobileNo+" is "+generatedBill.getTotalBillAmount(), HttpStatus.OK);
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

	@RequestMapping(value="/deleteCustomerPostpaidAccount/{mobileNo}",method=RequestMethod.DELETE)
	public ResponseEntity<String>deleteCustomerPostpaidAccount(@PathVariable("mobileNo")int mobileNo)throws CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException{
		boolean customer = services.closeCustomerPostPaidAccount(mobileNo, mobileNo);
		if(customer==false)throw new CustomerDetailsNotFoundException("Customer detail not found with mobile number"+mobileNo);
		return new ResponseEntity<>("Customer details succesfully deleted",HttpStatus.OK);

	}

	@RequestMapping(value= {"/getMonthlyBill"},produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bill>getMonthlyBill(@RequestParam("customerID") int customerID, @RequestParam("mobileNo") long mobileNo, @RequestParam("billMonth") String billMonth)  throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillingServicesDownException, PlanDetailsNotFoundException, BillDetailsNotFoundException{
		Bill monthlyBill= services.getMobileBillDetails(customerID, mobileNo, billMonth);

		System.out.println(monthlyBill);
		return new ResponseEntity<>(monthlyBill, HttpStatus.OK);
	}

	@RequestMapping(value={"/allPostpaidAccountBill"},produces=MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<List<Bill>> getAllPostpaidAccountBill(@RequestParam("customerID") int customerID,@RequestParam("mobileNo") long mobileNo) throws CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException, BillDetailsNotFoundException{
		List<Bill> bill= services.getCustomerPostPaidAccountAllBillDetails(customerID, mobileNo);
		return new ResponseEntity<>(bill,HttpStatus.OK);
	}

	@RequestMapping(value={"/allCustomerPostpaidAccount"},produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PostpaidAccount>> getAllCustomerPostpaidAccount(@RequestParam("customerID") int customerID) throws CustomerDetailsNotFoundException, BillingServicesDownException, PostpaidAccountNotFoundException, BillDetailsNotFoundException{
		List<PostpaidAccount> account= services.getCustomerAllPostpaidAccountsDetails(customerID);
		return new ResponseEntity<>(account,HttpStatus.OK);
	}
}