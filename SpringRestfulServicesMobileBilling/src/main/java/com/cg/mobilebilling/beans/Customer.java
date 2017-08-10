package com.cg.mobilebilling.beans;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int customerID;
	private String firstName, lastName, emailID, dateOfBirth,password;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy = "customer" , cascade= CascadeType.ALL,fetch=FetchType.EAGER)
	Map<Long,PostpaidAccount> postpaidAccounts=new HashMap<>();
	
	public Customer() {
		super();
	}

	public Customer(int customerID, String firstName, String lastName, String emailID, String dateOfBirth,
			String password, Address address, Map<Long, PostpaidAccount> postpaidAccounts) {
		super();
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailID = emailID;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.address = address;
		this.postpaidAccounts = postpaidAccounts;
	}
	

	public Customer(int customerID) {
		super();
		this.customerID = customerID;
	}

	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmailID() {
		return emailID;
	}


	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public Map<Long, PostpaidAccount> getPostpaidAccounts() {
		return postpaidAccounts;
	}


	public void setPostpaidAccounts(PostpaidAccount postpaidAccounts) {
		if(this.postpaidAccounts!=null){
		this.postpaidAccounts.put((long) (this.postpaidAccounts.size()+1),postpaidAccounts);	
		}else
		{
			System.out.println("null");
		}	
	}
}