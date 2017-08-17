package com.cg.mobilebilling.test;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import com.cg.mobilebilling.beans.Address;
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
import com.cg.mobilebilling.services.BillingServices;
import com.cg.mobilebilling.services.BillingServicesImpl;

public class MobileBillingTest {

	private static  BillingDAOServices billingDaoServices;
    private static  BillingServices billingServices;
	
	@BeforeClass
	public static  void setUpMobileBillingTestCases() {
		 billingDaoServices=Mockito.mock(BillingDAOServices.class);
		 billingServices=new BillingServicesImpl(billingDaoServices);
	}
	
	@Before
	public void setUpTestData() throws SQLException, PostpaidAccountNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException, PlanDetailsNotFoundException, InvalidBillMonthException {
		
		Map<Integer, Bill> bills= new HashMap<>();
		bills.put(1, new Bill(21, 3610, 5013, 81910, 5578,
				434234, "July", 4077582.5f, 21660f,
				30078f , 491460f, 33468f, 2559936f , 940980.6f));
		
		Map<Long,PostpaidAccount> postpaidAccounts=new HashMap<>();
		
		postpaidAccounts.put(1l, new PostpaidAccount(9866146526l, new Plan(499, 499, 434, 656, 534, 420, 7578, 6f, 6f, 6f, 6f, 6f, "Shandilya Nagar", "Tyagi Boys wala plan"), bills));
		
		Customer customer1= new Customer(6, "Hodor", "Gupta", "tyagi@hodor.com", "15-05-1999", "hodor", new Address(225256, "Gazia", "up"), postpaidAccounts);
	
		Customer customerTemp= new Customer(6, "Hodor", "Gupta", "tyagi@hodor.com", "15-05-1999", "hodor", new Address(225256, "Gazia", "up"));
		List<Customer> custList= new ArrayList<>();
		custList.add(customer1);
		
		List<Bill> billList= new ArrayList<>();
		
		Bill billTemp= new Bill(21, 3610, 5013, 81910, 5578,
				434234, "July", 4077582.5f, 21660f,
				30078f , 491460f, 33468f, 2559936f , 940980.6f);
		billList.add(billTemp);
		
		PostpaidAccount account= new PostpaidAccount(9866146526l, new Plan(199,199,200,300,400,400,3048,1f,1f,2f,2f,5f,"NCR","Gareebo wala plan"), bills);
		
		StandardPlan plan= new StandardPlan(199,199,200,300,400,400,3048,1f,1f,2f,2f,5f,"NCR","Gareebo wala plan");
		Bill bill=new Bill(3610, 5013, 81910, 5578,
				434234, "July", 4077582.5f, 21660f,
				30078f , 491460f, 33468f, 2559936f , 940980.6f);
		Mockito.when(billingDaoServices.getCustomer(6)).thenReturn(customer1);
		Mockito.when(billingDaoServices.getCustomerPostPaidAccount(6, 9866146526l)).thenReturn(postpaidAccounts.get(1l));
		Mockito.when(billingDaoServices.getMonthlyBill(9866146526l, "july")).thenReturn(bills.get(1));
		Mockito.when(billingDaoServices.getAllCustomers()).thenReturn(custList);
		Mockito.when(billingDaoServices.getCustomerPostPaidAccountAllBills(9866146526l)).thenReturn(billList);
		Mockito.when(billingDaoServices.getPostpaidAccountDetails(9866146526l)).thenReturn(postpaidAccounts.get(1l));
		Mockito.when(billingDaoServices.getsPlan(199)).thenReturn(plan);
		Mockito.when(billingDaoServices.updatePostPaidAccount(account)).thenReturn(account);
		Mockito.when(billingDaoServices.deletePostPaidAccount(9866146526l)).thenReturn(true);
		Mockito.when(billingServices.closeCustomerPostPaidAccount(9866146526l)).thenReturn(true);
		Mockito.when(billingDaoServices.deleteCustomer(6)).thenReturn(true);
		Mockito.when(billingServices.deleteCustomer(6)).thenReturn(true);
		Mockito.when(billingServices.changePlan(6, 9866146526l, 199)).thenReturn(account);
		Mockito.when(billingDaoServices.insertMonthlybill(9866146526l, bill)).thenReturn(billTemp);
		Mockito.when(billingServices.generateMonthlyMobileBill(9866146526l, "july", 2, 2, 2, 2, 2)).thenReturn(billTemp);
		Mockito.when(billingDaoServices.insertPostPaidAccount(customer1, postpaidAccounts.get(1l))).thenReturn(9866146526l);
		Mockito.when(billingServices.openPostpaidMobileAccount(6, 199)).thenReturn(9866146526l);
		Mockito.when(billingDaoServices.insertCustomer(customerTemp)).thenReturn(customerTemp);
		Mockito.when(billingServices.acceptCustomerDetails(customerTemp)).thenReturn(customerTemp);
	}
	
    @Test
	public void getPlanAllDetailsForValidData() throws BillingServicesDownException {
    	List<StandardPlan> expectedPlanList= billingDaoServices.getAllPlans();
		List<StandardPlan> actualPlanList= billingServices.getPlanAllDetails();
		assertEquals(expectedPlanList, actualPlanList);	
	}
	
	@Test
	public void acceptCustomerDetailsForValidData() throws BillingServicesDownException {
		
		Customer customer1= new Customer(6, "Hodor", "Gupta", "tyagi@hodor.com", "15-05-1999", "hodor", new Address(225256, "Gazia", "up"));
		Customer expectedCustomer=customer1;
		Customer actualCustomer=billingServices.acceptCustomerDetails(customer1);
		assertEquals(expectedCustomer, actualCustomer);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void openPostpaidAccountForInvalidData() throws CustomerDetailsNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException{
		billingServices.openPostpaidMobileAccount(23, 199);
	}
	
	/*@Test
	public void opopenPostpaidAccountForValidData() throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException {
		long expectedMobileNo = 9866146526l;
		long actualMobileNo= billingServices.openPostpaidMobileAccount(6,199);
		assertEquals(expectedMobileNo, actualMobileNo);
	}*/
	
	@Test (expected=PostpaidAccountNotFoundException.class)
	public void generateMonthlyBillForInvalidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillingServicesDownException, PlanDetailsNotFoundException {
		billingServices.generateMonthlyMobileBill(1l, "july", 2, 2, 2, 2, 2);
	}
	
	@Test 
	public void generateMonthlyBillForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillingServicesDownException, PlanDetailsNotFoundException {

		Bill billTemp= new Bill(21, 3610, 5013, 81910, 5578,
				434234, "July", 4077582.5f, 21660f,
				30078f , 491460f, 33468f, 2559936f , 940980.6f);
	
		Bill expectedBillId= billTemp;
		Bill actualBillId= billingServices.generateMonthlyMobileBill(9866146526l, "july", 2, 2, 2, 2, 2);
		assertEquals(expectedBillId, actualBillId);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void getCustomerDetailsForInvalidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		billingServices.getCustomerDetails(123);
	}
	
	@Test
	public void getCustomerDetailsForValidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		Customer expected= billingDaoServices.getCustomer(6);
		Customer actual= billingServices.getCustomerDetails(6);
		assertEquals(expected, actual);
	}
	
	@Test
	public void getAllCustomerDetails() throws BillingServicesDownException {
		List<Customer> expectedCustomerList= billingDaoServices.getAllCustomers();
		List<Customer> actualCustomerList= billingServices.getAllCustomerDetails();
		assertEquals(expectedCustomerList, actualCustomerList);	
	}
	
	@Test (expected=CustomerDetailsNotFoundException.class)
	public void getCustomerPostPaidAccountPlanDetailsForInvalidCustomerId() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, PlanDetailsNotFoundException {
		billingServices.getCustomerPostPaidAccountPlanDetails(121 , 9866146526l);
	}
	
	@Test (expected=PostpaidAccountNotFoundException.class)
	public void getCustomerPostPaidAccountPlanDetailsForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, PlanDetailsNotFoundException {
		billingServices.getCustomerPostPaidAccountPlanDetails(6, 121);
	}
	
	@Test
	public void getPostpaidAccountDetailsForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, PlanDetailsNotFoundException {
		
		Map<Integer, Bill> bills= new HashMap<>();
		bills.put(1, new Bill(21, 3610, 5013, 81910, 5578,
				434234, "July", 4077582.5f, 21660f,
				30078f , 491460f, 33468f, 2559936f , 940980.6f));
		
		Map<Long,PostpaidAccount> postpaidAccounts=new HashMap<>();
		
		postpaidAccounts.put(1l, new PostpaidAccount(9866146526l, new Plan(499, 499, 434, 656, 534, 420, 7578, 6f, 6f, 6f, 6f, 6f, "Shandilya Nagar", "Tyagi Boys wala plan"), bills));
		PostpaidAccount expectedPostpaidAccount= postpaidAccounts.get(1l); 
		PostpaidAccount actualPostpaidAccount= billingServices.getCustomerPostPaidAccountPlanDetails(6 , 9866146526l);
		assertEquals(expectedPostpaidAccount, actualPostpaidAccount);
	} 
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void getCustomerAllPostpaidAccountsDetailsForInvalidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		billingServices.getCustomerAllPostpaidAccountsDetails(1);
	}
	
	@Test
	public void getCustomerAllPostpaidAccountsDetailsForValidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		List<PostpaidAccount> expectedAccountList= new ArrayList<>(billingDaoServices.getCustomerPostPaidAccounts(6));
		List<PostpaidAccount> actualAccountList= billingServices.getCustomerAllPostpaidAccountsDetails(6);
		assertEquals(expectedAccountList, actualAccountList);
	}
	
	/*@Test(expected=PostpaidAccountNotFoundException.class)
	public void getMobileBillDetailsForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		billingServices.getMobileBillDetails(56, "july");
	}
	
	@Test(expected=InvalidBillMonthException.class)
	public void getMobileBillDetailsForInvalidMonth() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		billingServices.getMobileBillDetails(9866146526l, "august");
	}
	
	@Test(expected=BillDetailsNotFoundException.class)
	public void getMobileBillDetailsFirInvalidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		billingServices.getMobileBillDetails(89, "august");
	}*/
	
	@Test
	public void getMobileBillDetailsForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		Bill expectedBill= new Bill(21, 3610, 5013, 81910, 5578,
				434234, "July", 4077582.5f, 21660f,
				30078f , 491460f, 33468f, 2559936f , 940980.6f);
		Bill actualBill= billingServices.getMobileBillDetails(9866146526l, "july");
		assertEquals(expectedBill, actualBill);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void getCustomerPostPaidAccountAllBillDetailsForInvalidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, BillDetailsNotFoundException {
		billingServices.getCustomerPostPaidAccountAllBillDetails(9999999999l);
	}
	
	@Test
	public void getCustomerPostPaidAccountAllBillDetailsForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, BillDetailsNotFoundException {
		List<Bill> expectedBillList= billingDaoServices.getCustomerPostPaidAccountAllBills(9866146526l);
		List<Bill> actualBillList= billingServices.getCustomerPostPaidAccountAllBillDetails(9866146526l);
		assertEquals(expectedBillList, actualBillList);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void testChangePlanForInvalidCustomerId() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		billingServices.changePlan(7,9866146526l, 199);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void testChangePlanForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		billingServices.changePlan(6,33, 199);
	}
	
	@Test(expected=PlanDetailsNotFoundException.class)
	public void testChangePlanForInvalidPlanId() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		billingServices.changePlan(6,9866146526l, 299);
	}
	
	@Test
	public void testChangePlanForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		
		Map<Integer, Bill> bills= new HashMap<>();
		bills.put(1, new Bill(21, 3610, 5013, 81910, 5578,
				434234, "July", 4077582.5f, 21660f,
				30078f , 491460f, 33468f, 2559936f , 940980.6f));
		PostpaidAccount account= new PostpaidAccount(9866146526l, new Plan(199,199,200,300,400,400,3048,1f,1f,2f,2f,5f,"NCR","Gareebo wala plan"), bills);
		
		PostpaidAccount expectedAccount= billingDaoServices.updatePostPaidAccount(account);
		System.out.println("expectedAccount "+expectedAccount);
		PostpaidAccount actualAccount = billingServices.changePlan(6, 9866146526l, 199);
		assertEquals(expectedAccount, actualAccount);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void closeCustomerPostPaidAccountForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		billingServices.closeCustomerPostPaidAccount(96);
	}
	
	@Test
	public void closeCustomerPostPaidAccountForValidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		boolean expectedResponse= true;
		boolean actualResponse = billingServices.closeCustomerPostPaidAccount(9866146526l);
		assertEquals(expectedResponse, actualResponse);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void testDeleteCustomerForInvalidCustomerId() throws BillingServicesDownException, CustomerDetailsNotFoundException {
		billingServices.deleteCustomer(8);
	}
	
	@Test
	public void testDeleteCustomerForValidCustomerId() throws BillingServicesDownException, CustomerDetailsNotFoundException {
		boolean expectedResponse= true;
		boolean actualResponse = billingServices.deleteCustomer(6);
		assertEquals(expectedResponse, actualResponse);
	}
	
	
	
	@Test(expected=PlanDetailsNotFoundException.class)
	public void testGetsPlanForInvalidData() throws PlanDetailsNotFoundException {
		billingServices.getsPlan(78); 
	}
	
	@Test
	public void testGetsPlanForValidData() throws PlanDetailsNotFoundException {
		StandardPlan expectedPlan= billingDaoServices.getsPlan(199);
		StandardPlan actualPlan= billingServices.getsPlan(199);
		assertEquals(expectedPlan,actualPlan);
	}
}