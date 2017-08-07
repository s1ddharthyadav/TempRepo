package com.cg.mobilebilling.daoservices;
import java.util.List;

import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.Plan;
import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
public interface BillingDAOServices {
	int insertCustomer(Customer customer) throws BillingServicesDownException ;
	long insertPostPaidAccount(int customerID, PostpaidAccount account);
	boolean updatePostPaidAccount(int customerID, PostpaidAccount account);
	int insertMonthlybill(int customerID, long mobileNo, Bill bill);
	int insertPlan(Plan plan) throws PlanDetailsNotFoundException;
	boolean deletePostPaidAccount(int customerID, long mobileNo);
	Bill getMonthlyBill(int customerID, long mobileNo, String billMonth);
	List<Bill> getCustomerPostPaidAccountAllBills(int customerID, long mobileNo);
	List<PostpaidAccount> getCustomerPostPaidAccounts(int customerID);
	Customer getCustomer(int customerID);
	List<Customer>  getAllCustomers();
	List<Plan> getAllPlans();
	Plan getPlan(int planID) ;
	PostpaidAccount getCustomerPostPaidAccount(int customerID, long mobileNo);
	Plan getPlanDetails(int customerID, long mobileNo);
	boolean deleteCustomer(int customerID);
}