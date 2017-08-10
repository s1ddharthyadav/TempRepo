package com.cg.mobilebilling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.Plan;
import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.beans.StandardPlan;
import com.cg.mobilebilling.daoservices.BillingDAOServices;
import com.cg.mobilebilling.exceptions.BillDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.InvalidBillMonthException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;

@Service
@Transactional
public class BillingServicesImpl implements BillingServices {

	@Autowired
	BillingDAOServices dao;

	@Override
	public List<StandardPlan> getPlanAllDetails() throws BillingServicesDownException {
		return dao.getAllPlans();
	}

	@Override
	public Customer acceptCustomerDetails(Customer customer) throws BillingServicesDownException {
		return dao.insertCustomer(customer);
	}

	@Override
	public long openPostpaidMobileAccount(int customerID, int planID)
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException {

		StandardPlan sPlan= dao.getsPlan(planID);
		Plan plan= new Plan(sPlan.getPlanID(), sPlan.getMonthlyRental(), sPlan.getFreeLocalCalls(), sPlan.getFreeStdCalls(), sPlan.getFreeLocalSMS(), sPlan.getFreeStdSMS(), sPlan.getFreeInternetDataUsageUnits(), sPlan.getLocalCallRate(), sPlan.getStdCallRate(), sPlan.getLocalSMSRate(), sPlan.getStdSMSRate(), sPlan.getInternetDataUsageRate(),sPlan.getPlanCircle(), sPlan.getPlanName());
		PostpaidAccount account = new PostpaidAccount();
		long mobNo= generateUniqueMobileNo();
		account.setMobileNo(mobNo);
		account.setPlan(plan);
		return dao.insertPostPaidAccount(customerID, account);
	}

	@Override
	public Bill generateMonthlyMobileBill(long mobileNo, String billMonth, int noOfLocalSMS,
			int noOfStdSMS, int noOfLocalCalls, int noOfStdCalls, int internetDataUsageUnits)
					throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
					BillingServicesDownException, PlanDetailsNotFoundException
	{	
		PostpaidAccount p= dao.getPlanDetails(mobileNo);
		int BillednoOfLocalSMS = (noOfLocalSMS-p.getPlan().getFreeLocalSMS());
		int BillednoOfStdSMS = (noOfStdSMS-p.getPlan().getFreeStdSMS());
		int BillednoOfLocalCalls = (noOfLocalCalls-p.getPlan().getFreeLocalCalls());
		int BillednoOfStdCalls = (noOfStdCalls-p.getPlan().getFreeStdCalls());
		int BilledinternetDataUsageUnits = (internetDataUsageUnits-p.getPlan().getFreeInternetDataUsageUnits());
		float localCallAmount= BillednoOfLocalCalls*p.getPlan().getLocalCallRate();
		float StdCallAmount = BillednoOfStdCalls*p.getPlan().getStdCallRate();
		float LocalSMSAmount= BillednoOfLocalSMS*p.getPlan().getLocalSMSRate();
		float StdSMSAmount = BillednoOfStdSMS*p.getPlan().getStdSMSRate();
		float internetAmount = BilledinternetDataUsageUnits*p.getPlan().getInternetDataUsageRate();
		float gst= 0.30f;
		float Amount= localCallAmount+StdCallAmount+LocalSMSAmount+StdSMSAmount+internetAmount; 
		float gstAmount=  Amount*gst;
		float TotalBillAmount= Amount+gstAmount;

		Bill bill=new Bill(BillednoOfLocalSMS, BillednoOfStdSMS, BillednoOfLocalCalls, BillednoOfStdCalls, internetDataUsageUnits, billMonth, TotalBillAmount, LocalSMSAmount, StdSMSAmount, localCallAmount, StdCallAmount, internetAmount, gstAmount);
		return dao.insertMonthlybill(mobileNo, bill);
	}

	@Override
	public Customer getCustomerDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		return dao.getCustomer(customerID);
	}

	@Override
	public List<Customer> getAllCustomerDetails() throws BillingServicesDownException {
		return dao.getAllCustomers();
	}

	@Override
	public PostpaidAccount getPostPaidAccountDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		return null;
	}

	@Override
	public List<PostpaidAccount> getCustomerAllPostpaidAccountsDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		return dao.getCustomerPostPaidAccounts(customerID);
	}

	@Override
	public Bill getMobileBillDetails(long mobileNo, String billMonth)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, BillingServicesDownException {
		return dao.getMonthlyBill(mobileNo, billMonth);
	}

	@Override
	public List<Bill> getCustomerPostPaidAccountAllBillDetails(long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException,
			BillDetailsNotFoundException {
		return dao.getCustomerPostPaidAccountAllBills(mobileNo);
	}

	@Override
	public PostpaidAccount changePlan(int customerID, long mobileNo, int planID) throws CustomerDetailsNotFoundException,
	PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		StandardPlan sPlan= dao.getsPlan(planID);
		Plan plan= new Plan(sPlan.getPlanID(), sPlan.getMonthlyRental(), sPlan.getFreeLocalCalls(), sPlan.getFreeStdCalls(), sPlan.getFreeLocalSMS(), sPlan.getFreeStdSMS(), sPlan.getFreeInternetDataUsageUnits(), sPlan.getLocalCallRate(), sPlan.getStdCallRate(), sPlan.getLocalSMSRate(), sPlan.getStdSMSRate(), sPlan.getInternetDataUsageRate(),sPlan.getPlanCircle(), sPlan.getPlanName());
		PostpaidAccount account = new PostpaidAccount();
		Customer cust=new Customer(customerID);
		account.setCustomer(cust);
		account.setMobileNo(mobileNo);
		account.setPlan(plan);
		return dao.updatePostPaidAccount(account);
	}

	@Override
	public boolean closeCustomerPostPaidAccount(long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		return dao.deletePostPaidAccount(mobileNo);
	}

	@Override
	public boolean deleteCustomer(int customerID)
			throws BillingServicesDownException, CustomerDetailsNotFoundException {
		return dao.deleteCustomer(customerID);
	}

	@Override
	public PostpaidAccount getCustomerPostPaidAccountPlanDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException,
			PlanDetailsNotFoundException {
		return dao.getCustomerPostPaidAccount(customerID, mobileNo);
	}

	@Override
	public StandardPlan insertPlan(StandardPlan plan) throws PlanDetailsNotFoundException {
		return dao.insertPlan(plan);
	}

	@Override
	public StandardPlan getsPlan(int planID) {
		return dao.getsPlan(planID);
	}
	
	public long generateUniqueMobileNo() {
		long tempMobNo= (long) (Math.random()*1000000000);
		String var= "9000000000";
		long var1= Long.parseLong(var);
		long genMobNo= tempMobNo+var1;		
		return genMobNo;
	}
}