package com.patient;

import java.util.Random;

public class CustomerModel {
	@SuppressWarnings("unused")
	private String custID;
	private String custFirstName;
	private String custLastName;
	private String custEmailAddr;
	private long custPhoneNumber;
	
	public String getCustID() {
		return "cust" + new Random().nextInt(9999);
	}
	
	public String getCustFirstName() {
		return custFirstName;
	}
	
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	
	public String getCustLastName() {
		return custLastName;
	}
	
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	
	public String getCustEmailAddr() {
		return custEmailAddr;
	}
	
	public void setCustEmailAddr(String custEmailAddr) {
		this.custEmailAddr = custEmailAddr;
	}
	
	public long getCustPhoneNumber() {
		return custPhoneNumber;
	}
	
	public void setCustPhoneNumber(long custPhoneNumber) {
		this.custPhoneNumber = custPhoneNumber;
	}
	
}