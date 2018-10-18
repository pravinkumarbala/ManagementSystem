package com.doctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorModel {
	
	private SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
	
	private String firstName;
	private String lastName;
	private String specialization;
	private Date workingHourStart;
	private Date workingHourEnd;
	private int consultTime;
	
	public DoctorModel(String firstName, String lastName, String specialization, Date workingHourStart,
			Date workingHourEnd, String consultTime) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialization = specialization;
		this.workingHourStart = workingHourStart;
		this.workingHourEnd = workingHourEnd;
		this.consultTime = Integer.parseInt(consultTime);
	}

	public DoctorModel() { }

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
	
	public String getSpecialization() {
		return specialization;
	}
	
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	public Date getWorkingHourStart() {
		return workingHourStart;
	}
	
	public void setWorkingHourStart(String workingHourStart) throws ParseException {
		this.workingHourStart = hour.parse(workingHourStart);
	}
	
	public Date getWorkingHourEnd() {
		return workingHourEnd;
	}
	
	public void setWorkingHourEnd(String workingHourEnd) throws ParseException {
		this.workingHourEnd = hour.parse(workingHourEnd);
	}
	
	public int getConsultTime() {
		return consultTime;
	}

	public void setConsultTime(int consultTime) {
		this.consultTime = consultTime;
	}
	
}