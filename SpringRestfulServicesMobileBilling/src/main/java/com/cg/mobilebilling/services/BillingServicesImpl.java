package com.cg.mobilebilling.services;

import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
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

	/*private static final Exception CustomerDetailsNotFoundException = null;*/
	@Autowired
	BillingDAOServices dao;

	public BillingServicesImpl(BillingDAOServices dao) {
		super();
		this.dao = dao;
	}

	public BillingServicesImpl() {
	}

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

		StandardPlan sPlan= getsPlan(planID);
		Plan plan= new Plan(sPlan.getPlanID(), sPlan.getMonthlyRental(), sPlan.getFreeLocalCalls(), sPlan.getFreeStdCalls(), sPlan.getFreeLocalSMS(), sPlan.getFreeStdSMS(), sPlan.getFreeInternetDataUsageUnits(), sPlan.getLocalCallRate(), sPlan.getStdCallRate(), sPlan.getLocalSMSRate(), sPlan.getStdSMSRate(), sPlan.getInternetDataUsageRate(),sPlan.getPlanCircle(), sPlan.getPlanName());
		PostpaidAccount account = new PostpaidAccount();
		long mobNo= generateUniqueMobileNo();
		account.setMobileNo(mobNo);
		account.setPlan(plan);
		Customer customer= getCustomerDetails(customerID);
		return dao.insertPostPaidAccount(customer, account);
	}

	@Override
	public Bill generateMonthlyMobileBill(long mobileNo, String billMonth, int noOfLocalSMS,
			int noOfStdSMS, int noOfLocalCalls, int noOfStdCalls, int internetDataUsageUnits)
					throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
					BillingServicesDownException, PlanDetailsNotFoundException
	{	
		PostpaidAccount account= dao.getPostpaidAccountDetails(mobileNo);
		if(account==null) throw new PostpaidAccountNotFoundException("Postpaid Account for mobile number "+mobileNo+" not found");
		int BillednoOfLocalSMS = (noOfLocalSMS-account.getPlan().getFreeLocalSMS());
		int BillednoOfStdSMS = (noOfStdSMS-account.getPlan().getFreeStdSMS());
		int BillednoOfLocalCalls = (noOfLocalCalls-account.getPlan().getFreeLocalCalls());
		int BillednoOfStdCalls = (noOfStdCalls-account.getPlan().getFreeStdCalls());
		int BilledinternetDataUsageUnits = (internetDataUsageUnits-account.getPlan().getFreeInternetDataUsageUnits());
		float localCallAmount= BillednoOfLocalCalls*account.getPlan().getLocalCallRate();
		float StdCallAmount = BillednoOfStdCalls*account.getPlan().getStdCallRate();
		float LocalSMSAmount= BillednoOfLocalSMS*account.getPlan().getLocalSMSRate();
		float StdSMSAmount = BillednoOfStdSMS*account.getPlan().getStdSMSRate();
		float internetAmount = BilledinternetDataUsageUnits*account.getPlan().getInternetDataUsageRate();
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
		Customer cust=dao.getCustomer(customerID);
		if(cust==null)throw new CustomerDetailsNotFoundException("Customer details with Id "+customerID+" not found");
		return cust;
	}
	@Override
	public List<Customer> getAllCustomerDetails() throws BillingServicesDownException {
		return dao.getAllCustomers();
	}

	@Override
	public List<PostpaidAccount> getCustomerAllPostpaidAccountsDetails(int customerID)
			throws CustomerDetailsNotFoundException, BillingServicesDownException {
		Customer customer= getCustomerDetails(customerID);
		return dao.getCustomerPostPaidAccounts(customer.getCustomerID());
	}

	@Override
	public Bill getMobileBillDetails(long mobileNo, String billMonth)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException, BillingServicesDownException {
		/*PostpaidAccount account= dao.getPostpaidAccountDetails(mobileNo);
		if(account==null) throw new PostpaidAccountNotFoundException("Postpaid Account not fount for mobile number "+mobileNo);
		Bill bill= dao.getMonthlyBill(mobileNo, billMonth);
		if(bill==null) throw new InvalidBillMonthException("No bill exists for "+billMonth+" month");*/
		return dao.getMonthlyBill(mobileNo, billMonth);
	}

	@Override
	public List<Bill> getCustomerPostPaidAccountAllBillDetails(long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException,
			BillDetailsNotFoundException {

		PostpaidAccount account= dao.getPostpaidAccountDetails(mobileNo);		
		if(account==null) throw new PostpaidAccountNotFoundException("Postpaid Account not found" );
		return dao.getCustomerPostPaidAccountAllBills(mobileNo);
	}

	@Override
	public PostpaidAccount changePlan(int customerID, long mobileNo, int planID) throws CustomerDetailsNotFoundException,
	PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		System.out.println(planID);
		StandardPlan sPlan= dao.getsPlan(planID);
		if(sPlan==null) throw new PlanDetailsNotFoundException("Plan Details for Plan ID "+planID+" not found");
		System.out.println(planID+" "+mobileNo+" "+customerID);
		PostpaidAccount acc= dao.getPostpaidAccountDetails(mobileNo);
		if(acc==null) throw new PostpaidAccountNotFoundException("No postpaid account found");
		Plan plan= new Plan(sPlan.getPlanID(), sPlan.getMonthlyRental(), sPlan.getFreeLocalCalls(), sPlan.getFreeStdCalls(), sPlan.getFreeLocalSMS(), sPlan.getFreeStdSMS(), sPlan.getFreeInternetDataUsageUnits(), sPlan.getLocalCallRate(), sPlan.getStdCallRate(), sPlan.getLocalSMSRate(), sPlan.getStdSMSRate(), sPlan.getInternetDataUsageRate(),sPlan.getPlanCircle(), sPlan.getPlanName());
		PostpaidAccount account = new PostpaidAccount();
		Customer cust=getCustomerDetails(customerID);
		System.out.println(cust);
		account.setCustomer(cust);
		account.setMobileNo(mobileNo);
		account.setPlan(plan);
		System.out.println(account);
		PostpaidAccount accnt= dao.updatePostPaidAccount(account);
		System.out.println(accnt);
		return accnt;
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
		getCustomerDetails(customerID);
		PostpaidAccount account	= dao.getCustomerPostPaidAccount(customerID, mobileNo);
		if(account==null) throw new PostpaidAccountNotFoundException("Postpaid Account for mobile number "+mobileNo+" not found");
		return account;
	}

	@Override
	public StandardPlan insertPlan(StandardPlan plan) throws PlanDetailsNotFoundException {
		return dao.insertPlan(plan);
	}

	@Override
	public StandardPlan getsPlan(int planID) throws PlanDetailsNotFoundException {
		StandardPlan plan= dao.getsPlan(planID);
		if(plan==null) throw new PlanDetailsNotFoundException("Plan Details for Plan ID "+planID+" not found");
		return plan;
	}

	public long generateUniqueMobileNo() {
		long tempMobNo= (long) (Math.random()*1000000000);
		long var1= 9000000000l;
		long genMobNo= tempMobNo+var1;		
		return genMobNo;
	}
}