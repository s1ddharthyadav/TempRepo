package com.cg.mobilebilling.daoservices;

import java.util.List;
import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.Plan;
import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.beans.StandardPlan;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;

public interface BillingDAOServices {
	Customer insertCustomer(Customer customer) throws BillingServicesDownException ;
	long insertPostPaidAccount(int customerID, PostpaidAccount account);
	PostpaidAccount updatePostPaidAccount(PostpaidAccount account);
	Bill insertMonthlybill(long mobileNo, Bill bill);
	StandardPlan insertPlan(StandardPlan plan) throws PlanDetailsNotFoundException;
	Plan insertPlan(Plan plan) throws PlanDetailsNotFoundException;
	boolean deletePostPaidAccount(long mobileNo) throws PostpaidAccountNotFoundException;
	Bill getMonthlyBill(long mobileNo, String billMonth);
	List<Bill> getCustomerPostPaidAccountAllBills(long mobileNo);
	List<PostpaidAccount> getCustomerPostPaidAccounts(int customerID);
	Customer getCustomer(int customerID);
	List<Customer>  getAllCustomers();
	List<StandardPlan> getAllPlans();
	StandardPlan getsPlan(int planID) ;
	Plan getPlan(int planID) ;
	PostpaidAccount getCustomerPostPaidAccount(int customerID, long mobileNo);
	PostpaidAccount getPlanDetails(long mobileNo);
	boolean deleteCustomer(int customerID);
}