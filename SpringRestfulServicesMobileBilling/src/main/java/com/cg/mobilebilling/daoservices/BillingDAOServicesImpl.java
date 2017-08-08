package com.cg.mobilebilling.daoservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.Plan;
import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.beans.StandardPlan;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;


@Repository
public class BillingDAOServicesImpl implements BillingDAOServices {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Customer insertCustomer(Customer customer) throws BillingServicesDownException {
		em.persist(customer);
		em.flush();
		return customer;
	}

	@Override
	public long insertPostPaidAccount(int customerID, PostpaidAccount account) {
		Customer customer=em.find(Customer.class,customerID);
		account.setCustomer(customer);
		em.persist(account);
		customer.setPostpaidAccounts(account);
		return account.getMobileNo();
	}

	@Override
	public boolean updatePostPaidAccount(int customerID, PostpaidAccount account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bill insertMonthlybill(int customerID, long mobileNo, Bill bill) {
		PostpaidAccount postpaid=em.find(PostpaidAccount.class, mobileNo);
		bill.setPostpaidaccount(postpaid);
		em.persist(bill);
		postpaid.setBills(bill);
		return bill;
	}

	@Override
	public StandardPlan insertPlan(StandardPlan plan) throws PlanDetailsNotFoundException {
		em.persist(plan);
		em.flush();
		return plan;
	}

	@Override
	public boolean deletePostPaidAccount(int customerID, long mobileNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bill getMonthlyBill(int customerID, long mobileNo, String billMonth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bill> getCustomerPostPaidAccountAllBills(int customerID, long mobileNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostpaidAccount> getCustomerPostPaidAccounts(int customerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomer(int customerID) {
		Customer customer = em.find(Customer.class,customerID);
		return customer;
	}

	@Override
	public List<Customer> getAllCustomers() {
		TypedQuery<Customer> query = em.createQuery("select c from Customer c",Customer.class);
		return query.getResultList(); 
	}

	@Override
	public List<Plan> getAllPlans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StandardPlan getsPlan(int planID) {
		StandardPlan sPlan= em.find(StandardPlan.class, planID);
		return sPlan;
	}

	@Override
	public PostpaidAccount getCustomerPostPaidAccount(int customerID, long mobileNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public PostpaidAccount getPlanDetails(int customerID, long mobileNo) {
		PostpaidAccount plan=em.find(PostpaidAccount.class, mobileNo);
		return plan;
	}

	@Override
	public boolean deleteCustomer(int customerID) {
		em.remove(getCustomer(customerID));
		return true;
	}

	@Override
	public Plan insertPlan(Plan plan) throws PlanDetailsNotFoundException {
		// TODO Auto-generated method stub
		em.persist(plan);
		em.flush();
		return plan;
	}

	@Override
	public Plan getPlan(int planID) {
		Plan plan= em.find(Plan.class, planID);
		return plan;
	}
}