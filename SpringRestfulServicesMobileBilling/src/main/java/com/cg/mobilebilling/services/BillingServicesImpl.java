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
@Transactional/* (noRollbackFor=Exception.class)*/
public class BillingServicesImpl implements BillingServices {

	@Autowired
	BillingDAOServices dao;

	@Override
	public List<Plan> getPlanAllDetails() throws BillingServicesDownException {
		// TODO Auto-generated method stub
		return null;
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
		account.setPlan(plan);
		return dao.insertPostPaidAccount(customerID, account);
	}

	@Override
	public Bill generateMonthlyMobileBill(int customerID, long mobileNo, String billMonth, int noOfLocalSMS,
			int noOfStdSMS, int noOfLocalCalls, int noOfStdCalls, int internetDataUsageUnits)
					throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
					BillingServicesDownException, PlanDetailsNotFoundException
	{	
		PostpaidAccount p= dao.getPlanDetails(customerID, mobileNo);
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
		return dao.insertMonthlybill(customerID, mobileNo, bill);
	}

	@Override
	public Customer getCustomerDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomerDetails() throws BillingServicesDownException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostpaidAccount getPostPaidAccountDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostpaidAccount> getCustomerAllPostpaidAccountsDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		// TODO Auto-generated method stub
		return dao.getCustomerPostPaidAccounts(customerID);
	}

	@Override
	public Bill getMobileBillDetails(int customerID, long mobileNo, String billMonth)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, BillingServicesDownException {
		return dao.getMonthlyBill(customerID, mobileNo, billMonth);
	}

	@Override
	public List<Bill> getCustomerPostPaidAccountAllBillDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException,
			BillDetailsNotFoundException {
		return dao.getCustomerPostPaidAccountAllBills(customerID, mobileNo);
	}

	@Override
	public boolean changePlan(int customerID, long mobileNo, int planID) throws CustomerDetailsNotFoundException,
	PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeCustomerPostPaidAccount(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		
		return dao.deletePostPaidAccount(customerID, mobileNo);
	}

	@Override
	public boolean deleteCustomer(int customerID)
			throws BillingServicesDownException, CustomerDetailsNotFoundException {
		dao.deleteCustomer(customerID);
		return false;
	}

	@Override
	public Plan getCustomerPostPaidAccountPlanDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException,
			PlanDetailsNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StandardPlan insertPlan(StandardPlan plan) throws PlanDetailsNotFoundException {
		// TODO Auto-generated method stub
		return dao.insertPlan(plan);
	}

}