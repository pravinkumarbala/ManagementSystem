package com.appointment;

import java.util.Random;

public class AppointmentModel {
	
	@SuppressWarnings("unused")
	private String apptID;
	private String drID;
	private String custID;
	
	public String getApptID() {
		return "apt" + new Random().nextInt(9999999);
	}
	
	public String getDrID() {
		return drID;
	}
	
	public void setDrID(String drID) {
		this.drID = drID;
	}
	
	public String getCustID() {
		return custID;
	}
	
	public void setCustID(String custID) {
		this.custID = custID;
	}
	
}