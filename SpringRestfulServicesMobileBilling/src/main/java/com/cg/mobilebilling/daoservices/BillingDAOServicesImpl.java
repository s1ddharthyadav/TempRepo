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
	public PostpaidAccount updatePostPaidAccount(PostpaidAccount account) {
		PostpaidAccount accnt=em.merge(account);
		return accnt;
	}

	@Override
	public Bill insertMonthlybill(long mobileNo, Bill bill) {
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
	public boolean deletePostPaidAccount(long mobileNo) {
		PostpaidAccount account= em.find(PostpaidAccount.class, mobileNo);
		if(account!=null) {
			em.remove(account);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Bill getMonthlyBill(long mobileNo, String billMonth) {
		String query= "select b from Bill b where b.postpaidaccount.mobileNo=:mobileNo and b.billMonth=:billMonth";
		TypedQuery<Bill> qry= em.createQuery(query, Bill.class);
		qry.setParameter("mobileNo", mobileNo);
		qry.setParameter("billMonth", billMonth);
		Bill bill= qry.getSingleResult();
		System.out.println(bill.getGst());
		return bill;
	}

	@Override
	public List<Bill> getCustomerPostPaidAccountAllBills(long mobileNo) {
		String query= "select b from Bill b where b.postpaidaccount.mobileNo=:mobileNo";
		TypedQuery<Bill> qry= em.createQuery(query, Bill.class);
		qry.setParameter("mobileNo", mobileNo);
		return qry.getResultList();
	}

	@Override
	public List<PostpaidAccount> getCustomerPostPaidAccounts(int customerID) {
		String query= "select p from PostpaidAccount p where p.customer.customerID=:customerID";
		TypedQuery<PostpaidAccount> qry= em.createQuery(query, PostpaidAccount.class);
		qry.setParameter("customerID", customerID);
		return qry.getResultList();
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
	public List<StandardPlan> getAllPlans() {
		TypedQuery<StandardPlan> query = em.createQuery("select p from StandardPlan p",StandardPlan.class);
		return query.getResultList(); 
	}

	@Override
	public StandardPlan getsPlan(int planID) {
		StandardPlan sPlan= em.find(StandardPlan.class, planID);
		return sPlan;
	}

	@Override
	public PostpaidAccount getCustomerPostPaidAccount(int customerID, long mobileNo) {
		String query= "select p from PostpaidAccount p where p.customer.customerID=:customerID and p.mobileNo=:mobileNo";
		TypedQuery<PostpaidAccount> qry= em.createQuery(query, PostpaidAccount.class);
		qry.setParameter("customerID", customerID);
		qry.setParameter("mobileNo", mobileNo);
		return qry.getSingleResult();
	}

	public PostpaidAccount getPlanDetails(long mobileNo) {
		PostpaidAccount plan=em.find(PostpaidAccount.class, mobileNo);
		return plan;
	}

	@Override
	public boolean deleteCustomer(int customerID) {
		Customer customer = em.find(Customer.class,customerID);
		if(customer!=null) {
			em.remove(customer);
			return true;
		}else {
			return false;		
		}
	}

	@Override
	public Plan insertPlan(Plan plan) throws PlanDetailsNotFoundException {
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