package com.cg.mobilebilling.services;

import java.util.List;
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

public interface BillingServices {
	
	List<StandardPlan> getPlanAllDetails() throws BillingServicesDownException;
	
	StandardPlan insertPlan(StandardPlan plan) throws PlanDetailsNotFoundException;
	
	Customer acceptCustomerDetails(Customer customer) throws BillingServicesDownException;

	long openPostpaidMobileAccount(int customerID, int planID) 
			throws PlanDetailsNotFoundException,CustomerDetailsNotFoundException,
			BillingServicesDownException;
	
	Bill  generateMonthlyMobileBill(long mobileNo, String billMonth, int noOfLocalSMS, int noOfStdSMS, int noOfLocalCalls, int noOfStdCalls,int internetDataUsageUnits) 
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, 
			InvalidBillMonthException, BillingServicesDownException, 
			PlanDetailsNotFoundException;
	
	Customer getCustomerDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException;
	
	List<Customer> getAllCustomerDetails() throws BillingServicesDownException;
	
	PostpaidAccount getPostPaidAccountDetails(int customerID, long mobileNo) 
			throws CustomerDetailsNotFoundException, 
			PostpaidAccountNotFoundException, 
			BillingServicesDownException;
	
	List<PostpaidAccount> getCustomerAllPostpaidAccountsDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException;
	
	Bill getMobileBillDetails(long mobileNo, String billMonth)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, 
			InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException;
	
	List<Bill> getCustomerPostPaidAccountAllBillDetails(long mobileNo) 
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, 
			BillingServicesDownException, BillDetailsNotFoundException;
	
	PostpaidAccount changePlan(int customerID, long mobileNo, int planID)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, 
			PlanDetailsNotFoundException, BillingServicesDownException;
	
	boolean closeCustomerPostPaidAccount(long mobileNo) 
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, 
			BillingServicesDownException;
	
	boolean deleteCustomer(int customerID) 
			throws BillingServicesDownException, CustomerDetailsNotFoundException;
	
	PostpaidAccount getCustomerPostPaidAccountPlanDetails(int customerID, long mobileNo) 
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, 
			BillingServicesDownException, PlanDetailsNotFoundException ;
	
	StandardPlan getsPlan(int planID) ;
}