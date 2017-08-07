package com.cg.mobilebilling.exceptions;
@SuppressWarnings("serial")
public class BillingServicesDownException extends Exception {
	public BillingServicesDownException() {
		super();
	}
	public BillingServicesDownException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public BillingServicesDownException(String message, Throwable cause) {
		super(message, cause);
	}
	public BillingServicesDownException(String message) {
		super(message);
	}
	public BillingServicesDownException(Throwable cause) {
		super(cause);
	}
}