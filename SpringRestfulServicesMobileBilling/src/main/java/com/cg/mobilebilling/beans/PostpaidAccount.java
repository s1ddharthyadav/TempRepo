package com.cg.mobilebilling.beans;
import java.util.HashMap;

public class PostpaidAccount {
	private long mobileNo;
	private Plan plan;
	private  HashMap<Integer, Bill> bills;
	public PostpaidAccount() {}
	public PostpaidAccount(long mobileNo, Plan plan) {
		super();
		this.mobileNo = mobileNo;
		this.plan = plan;
		this.bills=new HashMap<>();
	}
	public PostpaidAccount(long mobileNo, Plan plan, HashMap<Integer, Bill> bills) {
		super();
		this.mobileNo = mobileNo;
		this.plan = plan;
		this.bills = bills;
	}
	public PostpaidAccount(Plan plan) {
		super();
		this.plan = plan;
		this.bills=new HashMap<>();
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public HashMap<Integer, Bill> getBills() {
		return bills;
	}
	public void setBills(int billIdx, Bill bill) {
		this.bills.put(billIdx, bill);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bills == null) ? 0 : bills.hashCode());
		result = prime * result + (int) (mobileNo ^ (mobileNo >>> 32));
		result = prime * result + ((plan == null) ? 0 : plan.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PostpaidAccount other = (PostpaidAccount) obj;
		if (bills == null) {
			if (other.bills != null) {
				return false;
			}
		} else if (!bills.equals(other.bills)) {
			return false;
		}
		if (mobileNo != other.mobileNo) {
			return false;
		}
		if (plan == null) {
			if (other.plan != null) {
				return false;
			}
		} else if (!plan.equals(other.plan)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "PostpaidAccount [mobileNo=" + mobileNo + ", plan=" + plan.toString()
				+ ", bills=" + bills.toString() + "]";
	}
}