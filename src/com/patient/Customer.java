package com.patient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public abstract class Customer extends CustomerModel{
	
	private Scanner scan = new Scanner(System.in);
	
	static File doctorFile = new File("Data/DoctorDetails.txt");
	static File appointmentFile = new File("Data/AppointmentDetails.txt");
	static File slotFile = new File("Data/SlotDetails.txt");
	static File customerFile = new File("Data/CustomerDetails.txt");
	
	static Set<String> customerList = new HashSet<>();
	static Map<String, String> customerHistory = new HashMap<>();
	
	/*
	 * Add the customer to File
	 */
	public String addCustomer() {
		String cId = getCustID();
		try {
			System.out.print("Customer First Name : ");
			setCustFirstName(scan.next());
			System.out.print("Customer Last Name : ");
			setCustLastName(scan.next());
			System.out.print("Customer Email Address : ");
			setCustEmailAddr(scan.next());
			System.out.print("Phone No : ");
			setCustPhoneNumber(scan.nextLong());
			BufferedReader cfr = new BufferedReader(new FileReader(customerFile));
			FileWriter cfw = new FileWriter(customerFile, true);
			if(cfr.readLine() == null)
				cfw.write("CustID \t"
						+ "CustFName\t"
						+ "CustLName\t"
						+ "CustEmail\t"
						+ "CustPhoneNo\n");
			cfw.write(cId + 
					"\t" + getCustFirstName() +
					"\t" + getCustLastName() +
					"\t" + getCustEmailAddr() +
					"\t" + getCustPhoneNumber() +
					"\n");
			cfw.close();
			cfr.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return cId;
	}
	
	/*
	 * Existing customer 
	 */
	public String existingCustomer(String email) {
		String cId = null;
		try {
			BufferedReader cfr = new BufferedReader(new FileReader(customerFile));
			String line = cfr.readLine();
			while(line != null) {
				if(line.contains(email)) {
					String[] sep = line.split("\t");
					cId = sep[0];
				}
				line = cfr.readLine();
			}
			cfr.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return cId;
	}
	
	/*
	 * Search Customer
	 */
	public void searchCustomer(String name) {
		try {
			BufferedReader cfr = new BufferedReader(new FileReader(customerFile));
			String customerLine = cfr.readLine();
			while(customerLine != null) {
				if(customerLine.contains(name)) {
					customerList.add(customerLine);
				}
				customerLine = cfr.readLine();
			}
			cfr.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Show Customer
	 */
	public void showCustomer() {
		for(String list: customerList) {
			int i = 1;
			String[] customer = list.split("\t");
			System.out.println(i + ": " + customer[1] + customer[2] + " --> " + customer[0]);
			i++;
		}
	}
	
	/*
	 * Pull Customer Details
	 */
	public void pullCustomer(String custId) {
		try {
			BufferedReader afr = new BufferedReader(new FileReader(appointmentFile));
			String line = afr.readLine();
			while(line != null) {
				if(line.contains(custId)) {
					String[] his = line.split("\t");
					  customerHistory.put(his[0], his[1]);
				}
				line = afr.readLine();
			}
			afr.close();
			System.out.println("Slot\t DoctorName\t DoctorSpecial");
			for(Map.Entry<String, String> bookslot: customerHistory.entrySet()) {
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
			for(Map.Entry<String, String> doctorDetail: customerHistory.entrySet()) {
				BufferedReader dfr = new BufferedReader(new FileReader(doctorFile));
				String doctorLine = dfr.readLine();
				while(doctorLine != null) {
					if(doctorLine.contains(doctorDetail.getValue())) {
						String[] doctor = doctorLine.split("\t");
						System.out.println(doctor[1] + doctor[2] + "\t" + doctor[3]);
					}
					doctorLine = dfr.readLine();
				}
				dfr.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}