package com.appointment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Appointment extends AppointmentModel{
	
	static File doctorFile = new File("Data/DoctorDetails.txt");
	static File appointmentFile = new File("Data/AppointmentDetails.txt");
	static File slotFile = new File("Data/SlotDetails.txt");
	static File customerFile = new File("Data/CustomerDetails.txt");
	
	static Map<String, String> doctorHistory = new HashMap<>();
	
	/*
	 * Book Appointment
	 */
	public String bookAppointment(String doctorId, String customerID) {
		String aptID = getApptID();
		try {
			setDrID(doctorId);
			setCustID(customerID);
			BufferedReader afr = new BufferedReader(new FileReader(appointmentFile));
			FileWriter afw = new FileWriter(appointmentFile, true);
			if(afr.readLine() == null)
				afw.write("ApptID\t"
						+ "DocID\t"
						+ "CusID\n");
			else 
				afw.write(aptID +
						"\t" + getDrID() +
						"\t" + getCustID() +
						"\n");
			afw.close();
			afr.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return aptID;
	}
	
	/*
	 * Pull Doctor Records 
	 */
	public void pullDocRec(String docId) {
		try {
			BufferedReader afr = new BufferedReader(new FileReader(appointmentFile));
			String line = afr.readLine();
			while(line != null) {
				if(line.contains(docId)) {
					String[] his = line.split("\t");
					  doctorHistory.put(his[0], his[2]);
				}
				line = afr.readLine();
			}
			afr.close();
			System.out.println("Slot\t CustomerName\t Customeremail");
			for(Map.Entry<String, String> bookslot: doctorHistory.entrySet()) {
				BufferedReader sfr = new BufferedReader(new FileReader(slotFile));
				String slotLine = sfr.readLine();
				while(slotLine != null) {
					if(slotLine.contains(bookslot.getKey())) {
						String[] slot = slotLine.split("\t");
						System.out.print(slot[1] + "\t");
					}
					slotLine = sfr.readLine();
				}
				sfr.close();
			}
			for(Map.Entry<String, String> customerdetail: doctorHistory.entrySet()) {
				BufferedReader cfr = new BufferedReader(new FileReader(customerFile));
				String customerLine = cfr.readLine();
				while(customerLine != null) {
					if(customerLine.contains(customerdetail.getValue())) {
						String[] customer = customerLine.split("\t");
						System.out.println(customer[1] + customer[2] + "\t" + customer[3] + "\n");
					}
					customerLine = cfr.readLine();
				}
				cfr.close();
				
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}