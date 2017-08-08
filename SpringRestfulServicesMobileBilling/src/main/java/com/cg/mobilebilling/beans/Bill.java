package com.cg.mobilebilling.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Bill {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int billID; 
	private int noOfLocalSMS, noOfStdSMS, noOfLocalCalls, noOfStdCalls, internetDataUsageUnits;
	private String billMonth;
	private float totalBillAmount, localSMSAmount, stdSMSAmount, localCallAmount, stdCallAmount, internetDataUsageAmount, servicesTax, vat;

	@ManyToOne
	private PostpaidAccount postpaidaccount;
	
	public Bill() {
		super();
	}

	public Bill(int billID, int noOfLocalSMS, int noOfStdSMS, int noOfLocalCalls, int noOfStdCalls,
			int internetDataUsageUnits, String billMonth, float totalBillAmount, float localSMSAmount,
			float stdSMSAmount, float localCallAmount, float stdCallAmount, float internetDataUsageAmount,
			float servicesTax, float vat, PostpaidAccount postpaidaccount) {
		super();
		this.billID = billID;
		this.noOfLocalSMS = noOfLocalSMS;
		this.noOfStdSMS = noOfStdSMS;
		this.noOfLocalCalls = noOfLocalCalls;
		this.noOfStdCalls = noOfStdCalls;
		this.internetDataUsageUnits = internetDataUsageUnits;
		this.billMonth = billMonth;
		this.totalBillAmount = totalBillAmount;
		this.localSMSAmount = localSMSAmount;
		this.stdSMSAmount = stdSMSAmount;
		this.localCallAmount = localCallAmount;
		this.stdCallAmount = stdCallAmount;
		this.internetDataUsageAmount = internetDataUsageAmount;
		this.servicesTax = servicesTax;
		this.vat = vat;
		this.postpaidaccount = postpaidaccount;
	}

	public int getBillID() {
		return billID;
	}

	public void setBillID(int billID) {
		this.billID = billID;
	}

	public int getNoOfLocalSMS() {
		return noOfLocalSMS;
	}

	public void setNoOfLocalSMS(int noOfLocalSMS) {
		this.noOfLocalSMS = noOfLocalSMS;
	}

	public int getNoOfStdSMS() {
		return noOfStdSMS;
	}

	public void setNoOfStdSMS(int noOfStdSMS) {
		this.noOfStdSMS = noOfStdSMS;
	}

	public int getNoOfLocalCalls() {
		return noOfLocalCalls;
	}

	public void setNoOfLocalCalls(int noOfLocalCalls) {
		this.noOfLocalCalls = noOfLocalCalls;
	}

	public int getNoOfStdCalls() {
		return noOfStdCalls;
	}

	public void setNoOfStdCalls(int noOfStdCalls) {
		this.noOfStdCalls = noOfStdCalls;
	}

	public int getInternetDataUsageUnits() {
		return internetDataUsageUnits;
	}

	public void setInternetDataUsageUnits(int internetDataUsageUnits) {
		this.internetDataUsageUnits = internetDataUsageUnits;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public float getTotalBillAmount() {
		return totalBillAmount;
	}

	public void setTotalBillAmount(float totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	public float getLocalSMSAmount() {
		return localSMSAmount;
	}

	public void setLocalSMSAmount(float localSMSAmount) {
		this.localSMSAmount = localSMSAmount;
	}

	public float getStdSMSAmount() {
		return stdSMSAmount;
	}

	public void setStdSMSAmount(float stdSMSAmount) {
		this.stdSMSAmount = stdSMSAmount;
	}

	public float getLocalCallAmount() {
		return localCallAmount;
	}

	public void setLocalCallAmount(float localCallAmount) {
		this.localCallAmount = localCallAmount;
	}

	public float getStdCallAmount() {
		return stdCallAmount;
	}

	public void setStdCallAmount(float stdCallAmount) {
		this.stdCallAmount = stdCallAmount;
	}

	public float getInternetDataUsageAmount() {
		return internetDataUsageAmount;
	}

	public void setInternetDataUsageAmount(float internetDataUsageAmount) {
		this.internetDataUsageAmount = internetDataUsageAmount;
	}

	public float getServicesTax() {
		return servicesTax;
	}

	public void setServicesTax(float servicesTax) {
		this.servicesTax = servicesTax;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}

	public PostpaidAccount getPostpaidaccount() {
		return postpaidaccount;
	}

	public void setPostpaidaccount(PostpaidAccount postpaidaccount) {
		this.postpaidaccount = postpaidaccount;
	}
}